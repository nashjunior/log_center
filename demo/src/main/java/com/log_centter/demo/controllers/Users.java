package com.log_centter.demo.controllers;

import javax.validation.Valid;

import com.log_centter.demo.entities.User;
import com.log_centter.demo.repos.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Users {

  @Autowired
  private UserRepo userRepo;

  /*
   * private BCryptPasswordEncoder bCryptPasswordEncoder;
   * 
   * public Users(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder){
   * this.bCryptPasswordEncoder=bCryptPasswordEncoder; this.userRepo=userRepo; }
   */

  @GetMapping("/login")
  public User login(@Valid @RequestBody User user) {
    return userRepo.findByEmail(user.getEmail());
  }

  @PostMapping("/signup")
  public User createUse(@Valid @RequestBody User user){
   // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepo.save(user);
  }


}