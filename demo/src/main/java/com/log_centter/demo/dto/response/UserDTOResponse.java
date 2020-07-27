package com.log_centter.demo.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTOResponse {
  public UserDTOResponse(String username, String jwt2, List<String> collect) {
    this.email = username;
    this.jwt = jwt2;
    this.roles = collect;
	}
private Long id;
  private String email;
  private String jwt;
  private List<String> roles;

}