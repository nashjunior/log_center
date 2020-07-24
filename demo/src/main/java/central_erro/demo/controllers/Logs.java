package central_erro.demo.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import central_erro.demo.dto.request.LogsDTORequest;
import central_erro.demo.entities.Log;
import central_erro.demo.services.interfaces.LogInterface;

@RestController
@RequestMapping("/api/logs")
public class Logs {

  @Autowired
  LogInterface logInterface;

  @Autowired
  ObjectMapper object;

  @GetMapping
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
  public ResponseEntity<Log> createLog(@Valid @RequestBody final LogsDTORequest incomingLog) {
    ModelMapper modelMapper = new ModelMapper();
    Log newLog = modelMapper.map(incomingLog, Log.class);
    logInterface.save(newLog);
    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(newLog);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Log> updateLog(@PathVariable("id") Long id,
      @Valid @RequestBody LogsDTORequest request) {

    Optional<Log> log = logInterface.findById(id);
    if (!log.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    try {
      object.updateValue(log.get(), request);
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      return ResponseEntity.badRequest().build();
    }
    

    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(log.get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Log> deleteLog(@PathVariable("id") Long id) {
    return logInterface.deleteById(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
  }

}