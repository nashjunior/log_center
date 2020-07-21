package com.log_centter.demo.security.authentication.user_details;

import com.log_centter.demo.entities.User;
import com.log_centter.demo.repos.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
/* import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException; */
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl{}/* implements UserDetailsService {

  @Autowired
  UserRepo userRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(email).orElseThrow(() -> 
      new UsernameNotFoundException("User not found with this email: "+email));
    return UserDetailsImpl.build(user);
  }
  
} */