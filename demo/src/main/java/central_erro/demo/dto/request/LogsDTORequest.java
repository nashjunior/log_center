package central_erro.demo.dto.request;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import central_erro.demo.dto.DateDesserializer;
import central_erro.demo.entities.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogsDTORequest {
  private Long id;
  private LogLevel level;
  private String eventDescription;
  private String origin;
  
  @JsonDeserialize(using = DateDesserializer.class)
  private LocalDateTime date;
  private Long quantity;
}