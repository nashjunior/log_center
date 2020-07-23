package com.log_centter.demo.controllers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.log_centter.demo.dto.request.LogsDTORequest;
import com.log_centter.demo.entities.Log;
import com.log_centter.demo.security.authentication.jwt.JwtUtils;
import com.log_centter.demo.services.interfaces.LogInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    System.out.println("chegou aqui");
    reqParam.entrySet()
        .removeIf(param -> param.getValue() == null || Arrays.asList(Log.class.getDeclaredFields()).stream()
            .filter(field -> field.getName().equals(param.getKey())).collect(Collectors.toList()).isEmpty() == true);

    if (reqParam.size() > 0) {
      final List<?> logs = logInterface.findAllLogsByParam(reqParam);
      if (logs.size() > 0) {
        return ResponseEntity.ok(logs);
      }
    }
    return ResponseEntity.ok(logInterface.findAll());
  }

  @PostMapping
  @ResponseBody
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<Log> createLog(@Valid @RequestBody final Log newLog) {
    Log savedLog;
    try {
      savedLog = logInterface.save(newLog);
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(savedLog);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<LogsDTORequest> updateLog(@PathVariable("id") Long id,@Valid @RequestBody LogsDTORequest logUpdated) {

    Optional<Log> log = logInterface.findById(id);
    if (!log.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    try {
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(logUpdated);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<Log> deleteLog(@PathVariable("id") Long id) {
    return logInterface.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
  }

}