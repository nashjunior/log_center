package com.log_centter.demo.security.authentication.user_details;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.log_centter.demo.entities.User;

/* import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; */

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserDetailsImpl{} /* implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;

	private String email;

	@JsonIgnore
  private String password;
  
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id2, String email2, String password2, List<GrantedAuthority> authorities2) {
    this.id = id2;
    this.email = email2;
    this.password = password2;
    this.authorities = authorities2;
  }

  public static UserDetailsImpl build(User user) {
    String [] roles = user.getAdmin() == true? new String []{"USER", "ADMIN"} : new String[] {"USER"};

    List <GrantedAuthority> authorities = Arrays.asList(roles).stream().
      map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    
    return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword(), authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) obj;
    return Objects.equals(id, user.id);
  }
  
} */