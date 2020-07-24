package com.log_centter.demo.services.implementations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
    List<Map.Entry<String, Object>> list2= new ArrayList<>(params.entrySet());
    
    //Comparando as chaves e colocando o size como ultima chave
    Collections.sort(list2, (obj1, obj2) -> {
      if(!obj1.getKey().equals("size") && !obj1.getKey().equals("page")) return -1;
      else if(obj1.getKey().equals("size") || obj2.getKey().equals("page")) return 1;

      return 0;
    });
    params.clear();
    list2.forEach(obj -> params.put(obj.getKey(), obj.getValue()));

    for (Map.Entry<String, Object> param : params.entrySet()) {
      //verifica se parametro e tipo date
      if (param.getKey().equals("date") && isValidDate(param.getValue().toString())) {
        sqlSearch = sqlSearch
            .concat("date(" + param.getKey() + ")=to_date('" + param.getValue().toString() + "','DD/MM/YYYY') AND ");
      }
      //verifica se o parametro não e de paginacao 
      else if (!param.getKey().equals("page") && !param.getKey().equals("size")) {
        sqlSearch = sqlSearch.concat(param.getKey() + "='" + param.getValue().toString() + "' AND ");
      } else if (param.getKey().equals("page") && params.containsKey("size")) {
        sqlSearch = sqlSearch.substring(0, sqlSearch.length() - 4);
        try {
          Integer.parseInt(param.getValue().toString());
          sqlSearch = sqlSearch.concat("OFFSET " + Integer.valueOf(param.getValue().toString()) + " ");
        } catch (NumberFormatException | NullPointerException e) {
          return null;
        }
      } else {
        try {
          Integer.parseInt(param.getValue().toString());
          sqlSearch = sqlSearch.concat("LIMIT " + Integer.valueOf(param.getValue().toString()) + " ");
        } catch (NumberFormatException | NullPointerException e) {
          return null;
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