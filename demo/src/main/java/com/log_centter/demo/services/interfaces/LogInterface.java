package com.log_centter.demo.services.interfaces;

import java.util.List;
import java.util.Map;
import com.log_centter.demo.entities.Log;

public interface LogInterface {
  List<Log> findAllLogs();

  List<?> findAllLogsByParam(Map<String, Object> params);

  Log createLog(Log log);

}