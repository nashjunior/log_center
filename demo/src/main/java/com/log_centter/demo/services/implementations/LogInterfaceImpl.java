package com.log_centter.demo.services.implementations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
      if (param.getKey().equals("date") && isValidDate(param.getValue().toString())) {
        sqlSearch = sqlSearch
            .concat("date(" + param.getKey() + ")=to_date('" + param.getValue().toString() + "','DD-MM-YYYY') AND ");
      } else if (!param.getKey().equals("page") && !param.getKey().equals("size")) {
        sqlSearch = sqlSearch.concat(param.getKey() + "='" + param.getValue().toString() + "' AND ");
      } else if (param.getKey().equals("page") && params.containsKey("size")) {
        try {
          Integer.parseInt(param.getValue().toString());
          sqlSearch = sqlSearch.concat("OFFSET " + Integer.valueOf(param.getValue().toString()) + " ");
        } catch (NumberFormatException | NullPointerException e) {
        }
      } else {
        try {
          Integer.parseInt(param.getValue().toString());
          sqlSearch = sqlSearch.concat("LIMIT " + Integer.valueOf(param.getValue().toString()) + " ");
        } catch (NumberFormatException | NullPointerException e) {
        }
      }
    }
    if (!params.containsKey("size")) {
      sqlSearch = sqlSearch.substring(0, sqlSearch.length() - 4);
    }
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
  public List<Log> findAll() {
    return logRepo.findAll();
  }

  @Override
  public Log save(Log log) {
    return logRepo.save(log);
  }

  public static Boolean isValidDate(final String inDate) {
    final SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat2.setLenient(false);
    try {
      dateFormat2.parse(inDate.trim());
      return true;
    } catch (final ParseException pe) {
      return false;
    }
  }

  @Override
  public Optional<Log> findById(Long id) {
    return logRepo.findById(id);
  }

  public Boolean deleteById(Long id) {
    try {
      logRepo.deleteById(id);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

}