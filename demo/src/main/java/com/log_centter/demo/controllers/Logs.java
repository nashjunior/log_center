package com.log_centter.demo.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.log_centter.demo.entities.Log;
import com.log_centter.demo.security.authentication.jwt.JwtUtils;
import com.log_centter.demo.services.interfaces.LogInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
  JwtUtils jwtIUtils;

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  private ResponseEntity<List<?>> getAllLogs(@RequestParam final Map<String, Object> reqParam)
      throws NoSuchAlgorithmException {
    reqParam.entrySet()
        .removeIf(param -> param.getValue() == null || Arrays.asList(Log.class.getDeclaredFields()).stream()
            .filter(field -> field.getName().equals(param.getKey())).collect(Collectors.toList()).isEmpty() == true);

    if (reqParam.size() > 0) {
      final List<?> logs = logInterface.findAllLogsByParam(reqParam);
      if (logs.size() > 0) {
        return ResponseEntity.ok(logs);
      }
    }
    return ResponseEntity.ok(logInterface.findAllLogs());
  }

  @PostMapping
  @ResponseBody
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  private ResponseEntity<Log> createLog(@Valid @RequestBody final Log newLog) {
    Log savedLog;
    try {
      savedLog = logInterface.createLog(newLog);
    } catch (final Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(savedLog);
  }
}