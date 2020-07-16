package com.log_centter.demo.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import com.log_centter.demo.entities.Log;
import com.log_centter.demo.repos.LogRepo;

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
  EntityManager em;
  
  @Autowired
  private LogRepo logRepo;

  @GetMapping("/logs")
  private ResponseEntity<List<?>> getAllLogs(@RequestParam Map<String, Object> reqParam) {
    List<?> list = new ArrayList<>();
    String sqlSearch = "SELECT l.* from Log l where ";
    if (reqParam.size() > 0) {
      
      for (Map.Entry<String, Object> param : reqParam.entrySet()) {
        if (param.getValue() != null) {
          System.out.println(param.getValue());
          if(isValidDate( param.getValue().toString())) {
            sqlSearch = sqlSearch.concat("date("+param.getKey().toString() + ")=to_date('" + param.getValue().toString() + "','DD-MM-YYYY') AND ");
          }
          else if ((param.getValue() instanceof String && !param.getValue().toString().trim().isEmpty())
              || param.getValue() instanceof Integer) {
            sqlSearch = sqlSearch.concat(param.getKey().toString() + "='" + param.getValue().toString() + "' AND ");
          }
        }
      }
      sqlSearch = sqlSearch.substring(0, sqlSearch.length() - 4);
      
      Query query = em.createNativeQuery(sqlSearch, Log.class);
      try {
        list = query.getResultList(); 
        return ResponseEntity.ok(list);
      } catch (RuntimeException e) {
        //TODO: handle exception
        return ResponseEntity.badRequest().build();
      }
    }
    return ResponseEntity.ok(logRepo.findAll());
  }

  @PostMapping("/logs")
  @ResponseBody
  private ResponseEntity<Log> createLog(@Valid @RequestBody Log newLog) {
    Log savedLog;
    try {
      savedLog =  logRepo.save(newLog);
    } catch (Exception e) {
      //TODO: handle exception
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(savedLog);
  }

  public static boolean isValidDate(String inDate) {
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat2.setLenient(false);
    try {
      dateFormat2.parse(inDate.trim());
        System.out.println("aqui");
        return true;
    } catch (ParseException pe) {
        return false;
    }
}
}