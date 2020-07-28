package com.log_centter.demo.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTOResponse {
  public UserDTOResponse(String username, String jwt2, List<String> collect) {
    this.email = username;
    this.jwt = jwt2;
    this.roles = collect;
  }
  
  public UserDTOResponse(String username, String password, Boolean isAdmin) {
    this.email = username;
    this.password = password;
    this.isAdmin = isAdmin;
	}
  private Long id;
  private String email;
  private String jwt;
  private String password;
  private Boolean isAdmin;
  private List<String> roles;

}