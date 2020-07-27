package com.log_centter.demo.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.log_centter.demo.dto.request.UserDTORequest;
import com.log_centter.demo.dto.response.UserDTOResponse;
import com.log_centter.demo.entities.User;
import com.log_centter.demo.repos.UserRepo;
import com.log_centter.demo.security.authentication.jwt.JwtUtils;
import com.log_centter.demo.security.authentication.user_details.UserDetailsImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/")
public class Users {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  private JwtUtils jwtUtils;

  @GetMapping("/login")
  private ResponseEntity<UserDTOResponse> login(@Valid @RequestBody UserDTORequest user) throws NoSuchAlgorithmException {
    Optional<User> login = userRepo.findByEmail(user.getEmail());
    if (!login.isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return ResponseEntity.ok(new UserDTOResponse(userDetails.getId(),userDetails.getUsername(), jwt, 
      userDetails.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList())));
  }

  @PostMapping("/signup")
  private ResponseEntity<User> createUse(@Valid @RequestBody UserDTORequest user) {
    if (userRepo.findByEmail(user.getEmail()).isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    user.setPassword(encoder.encode(user.getPassword()));
    User newUser = userRepo.save(user.buildUser());
    return newUser == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(newUser);
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> index(){
    return ResponseEntity.ok(userRepo.findAll());
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody UserDTORequest fieldToUpdate){
    Optional<User> user=userRepo.findById(id);
    if(!user.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setSkipNullEnabled(true);
    if(fieldToUpdate.getPassword()!=null || fieldToUpdate.getPassword().trim().isEmpty()){
      fieldToUpdate.setPassword(encoder.encode(fieldToUpdate.getPassword()));
    }
    mapper.map(fieldToUpdate,user.get());
    userRepo.save(user.get());
    
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<User> delete(@PathVariable("id") Long id) {
    Optional<User> user=userRepo.findById(id);
    if(!user.isPresent()){
      return ResponseEntity.badRequest().build();
    }
    userRepo.delete(user.get());
    return ResponseEntity.ok().build();
  }

}