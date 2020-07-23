package com.log_centter.demo.dto.request;

import java.util.Date;

import com.log_centter.demo.entities.LogLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsDTORequest {
  private Long id;
  private LogLevel logLevel;
  private String eventDescription;
  private String origin;
  private Date date;
  private Long quantity;
}