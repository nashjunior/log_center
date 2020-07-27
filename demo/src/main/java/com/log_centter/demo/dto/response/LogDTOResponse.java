package com.log_centter.demo.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.log_centter.demo.deserializers.DateSerializer;
import com.log_centter.demo.entities.Log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTOResponse {
  private Long id;
  private String origin;

  @JsonSerialize(using = DateSerializer.class)
  private LocalDateTime date;
  private Long quantity;

  public LogDTOResponse (Object log){
    this.date = ((Log) log).getDate();
    this.origin = ((Log) log).getOrigin();
    this.id = ((Log) log).getId();
    this.quantity = ((Log) log).getQuantity();
  }
}