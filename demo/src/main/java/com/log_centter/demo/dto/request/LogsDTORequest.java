package com.log_centter.demo.dto.request;

import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.log_centter.demo.dto.DateDesserializer;
import com.log_centter.demo.entities.LogLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsDTORequest {
  private Optional<Long> id;
  private Optional<LogLevel> logLevel;
  private Optional<String> eventDescription;
  private Optional<String> origin;
  @JsonDeserialize(using = DateDesserializer.class)
  private Optional<Date> date;
  private Optional<Long> quantity;
}