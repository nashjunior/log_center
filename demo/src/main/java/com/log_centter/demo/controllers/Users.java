package com.log_centter.demo.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import com.log_centter.demo.entities.User;
import com.log_centter.demo.repos.UserRepo;
import com.log_centter.demo.security.authentication.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class Users {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private JwtUtils jwtUtils;

  @GetMapping("/login")
  public User login(@Valid @RequestBody User user) throws NoSuchAlgorithmException {
    this.jwtUtils.getPrivateKey();
    this.jwtUtils.getEncodedPublicKey();
    return userRepo.findByEmail(user.getEmail()).get();
  }

  @PostMapping("/signup")
  public User createUse(@Valid @RequestBody User user){
    return userRepo.save(user);
  }


}