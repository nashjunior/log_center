package com.log_centter.demo.dto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateDesserializer extends StdDeserializer<Date> {
  private static final long serialVersionUID = 1L;

  private static final String[] DATE_FORMATS = new String[] { "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy" };

  public DateDesserializer() {
    this(null);
  }

  public DateDesserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    final String date = node.textValue();

    for (String DATE_FORMAT : DATE_FORMATS) {
      try {
        return new SimpleDateFormat(DATE_FORMAT).parse(date);
      } catch (ParseException e) {
      }
    }
    throw new JsonParseException(jp,
        "Unparseable date: \"" + date + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
  }
}