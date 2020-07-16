package com.log_centter.demo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.log_centter.demo.entities.Log;
import com.log_centter.demo.services.interfaces.LogInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Logs {

  @Autowired
  LogInterface logInterface;

  @GetMapping("/logs")
  private ResponseEntity<List<?>> getAllLogs(@RequestParam final Map<String, Object> reqParam) {
    reqParam.entrySet()
        .removeIf(param -> param.getValue() == null || Arrays.asList(Log.class.getDeclaredFields()).stream()
        .filter(field -> field.getName().equals(param.getKey())).collect(Collectors.toList()).isEmpty() ==true);
    System.out.println(reqParam.size());

    if (reqParam.size() > 0) {
      final List<?> logs = logInterface.findAllLogsByParam(reqParam);
      if (logs.size() > 0) {
        return ResponseEntity.ok(logs);
      }
    }
    return ResponseEntity.ok(logInterface.findAllLogs());
  }

  @PostMapping("/logs")
  @ResponseBody
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