package com.log_centter.demo.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log_centter.demo.dto.request.LogsDTORequest;
import com.log_centter.demo.entities.Log;
import com.log_centter.demo.security.authentication.jwt.JwtUtils;
import com.log_centter.demo.services.interfaces.LogInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class Logs {

  @Autowired
  LogInterface logInterface;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  JwtUtils jwtIUtils;

  @GetMapping
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<List<?>> getAllLogs(@RequestParam Map<String, Object> reqParam) {
    if (reqParam.containsKey("page") && reqParam.containsKey("size")) {
      reqParam.entrySet()
          .removeIf(param -> param.getValue().toString().trim().isEmpty()
              || ((!param.getKey().equals("page") && !param.getKey().equals("size")
                  && Arrays.asList(Log.class.getDeclaredFields()).stream()
                      .filter(field -> field.getName().equals(param.getKey())).collect(Collectors.toList())
                      .isEmpty() == true)));
    } else {
      reqParam.entrySet()
          .removeIf(param -> param.getValue() == null || Arrays.asList(Log.class.getDeclaredFields()).stream()
              .filter(field -> field.getName().equals(param.getKey())).collect(Collectors.toList()).isEmpty() == true);
    }
    System.out.println(reqParam.size());
    if (!reqParam.containsKey("size") && reqParam.size() > 0) {
      final List<?> logs = logInterface.findAllLogsByParam(reqParam);
      if (logs.size() > 0) {
        return ResponseEntity.ok(logs);
      }
    } else if (reqParam.containsKey("size") && reqParam.size() > 2) {
      final List<?> logs = logInterface.findAllLogsByParam(reqParam);
      if (logs.size() > 0) {
        return ResponseEntity.ok(logs);
      }
    }

    return ResponseEntity.ok(logInterface.findAll());
  }

  @PostMapping
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  @ResponseBody
  public ResponseEntity<Log> createLog(@Valid @RequestBody final LogsDTORequest incomingLog) {
    ModelMapper modelMapper = new ModelMapper();
    Log newLog = modelMapper.map(incomingLog, Log.class);
    logInterface.save(newLog);
    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(newLog);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<Log> deleteLog(@PathVariable("id") Long id) {
    return logInterface.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
  }

}