package com.log_centter.demo.dto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateDesserializer extends StdDeserializer<Date> {
  private static final long serialVersionUID = 1L;

  private static final String[] DATE_FORMATS = new String[] { "dd/MM/yyyy-HH:mm:ss", "dd/MM/yyyy" };

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
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        return dateFormat.parse(date.trim());
      } catch (ParseException e) {
        System.out.println(e.getMessage());
      }
    }
    throw new JsonParseException(jp,
        "Unparseable date: \"" + date + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
  }
}