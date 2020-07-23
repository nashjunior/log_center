package com.log_centter.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.log_centter.demo.dto.DateDesserializer;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @NotNull
  @Enumerated(EnumType.STRING)
  private LogLevel level;

  @Column(name = "event_description")
  @NotNull
  private String eventDescription;

  @Column
  @NotNull
  private String origin;

  @NotNull
  @JsonProperty("date")
  @JsonDeserialize(using = DateDesserializer.class)
  private Date date;

  @Column
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long quantity;

}