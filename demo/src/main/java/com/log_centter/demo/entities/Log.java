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

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Log {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long id;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column
  @NotNull
  @Enumerated(EnumType.STRING)
  private LogLevel level;

  public LogLevel getLevel() {
    return this.level;
  }

  public void setLevel(LogLevel level) {
    this.level = level;
  }

  @Column(name = "event_description")
  @NotNull
  private String eventDescription;

  public String getEventDescription() {
    return this.eventDescription;
  }

  public void setEventDescription(String eventDescription) {
    this.eventDescription = eventDescription;
  }

  @Column
  @NotNull
  private String origin;

  public String getOrigin() {
    return this.origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @NotNull
  @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE, locale = JsonFormat.DEFAULT_LOCALE )
  private Date date;

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Column
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long quantity;

  public Long getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
  
}