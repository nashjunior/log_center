package com.log_centter.demo.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.log_centter.demo.entities.Log;
import com.log_centter.demo.repos.LogRepo;
import com.log_centter.demo.services.interfaces.LogInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LogInterfaceImpl implements LogInterface {

  @Autowired
  EntityManager em;

  @Autowired
  private LogRepo logRepo;

  @Override
  public List<?> findAllLogsByParam(Map<String, Object> params) {
    String sqlSearch = "SELECT l.* from Log l where ";
    List<?> list = new ArrayList<>();

    for (Map.Entry<String, Object> param : params.entrySet()) {
      if (param.getValue() instanceof Date) {
        sqlSearch = sqlSearch.concat("date(" + param.getValue().toString() + ")=to_date('" + param.getValue().toString()
            + "','DD-MM-YYYY') AND ");
      } else {
        sqlSearch = sqlSearch.concat(param.getValue().toString() + "='" + param.getValue().toString() + "' AND ");
      }
    }

    sqlSearch = sqlSearch.substring(0, sqlSearch.length() - 4);
    System.out.println(sqlSearch);
    Query query = em.createNativeQuery(sqlSearch, Log.class);
    try {
      list = query.getResultList();
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    return list;

  }

  @Override
  public List<Log> findAllLogs() {
    return logRepo.findAll();
  }

  @Override
  public Log createLog(Log log) {
    return logRepo.save(log);
  }

}