package com.log_centter.demo.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.log_centter.demo.entities.Log;

public interface LogInterface extends GenericInterface<Log>{

  @Override
  List<Log> findAllList();

  Optional<Log> findById(Long id);

  Boolean deleteById(Long id);

  List<?> findAllLogsByParam(Map<String, String> params, Map <String, String> ordersString , Boolean isFilter,Boolean isSized, Boolean isPageable);

}