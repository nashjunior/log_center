package com.log_centter.demo.dto.request;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.log_centter.demo.dto.DateDesserializer;
import com.log_centter.demo.entities.LogLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogsDTORequest {
  private Optional<Long> id;
  private Optional<LogLevel> level;
  private Optional<String> eventDescription;
  private Optional<String> origin;
  @JsonDeserialize(using = DateDesserializer.class)
  private Optional<Date> date;
  private Optional<Long> quantity;
}