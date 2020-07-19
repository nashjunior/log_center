package com.log_centter.demo.security.authentication.jwt;

import java.security.PrivateKey;
import java.util.Date;

import com.log_centter.demo.security.authentication.user_details.UserDetailsImpl;
import com.log_centter.demo.security.key.GenerateRSA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
  
  @Autowired
  GenerateRSA rsaKey;

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  private final String encodedPublicKey = rsaKey.getEncodedPublicKey();
  private final PrivateKey privateKey = rsaKey.getPrivateKey();

  @Value("${jwt.expirationTimeInMS}")
  private int jwtExpirationTime;

  @SuppressWarnings("deprecation")
  public String generateJwtToken(Authentication authentication){
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    String token = Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
    .setExpiration(new Date(new Date().getTime() + jwtExpirationTime) ).claim("groups", userPrincipal.getAuthorities())
    .signWith(SignatureAlgorithm.HS512, privateKey).compact();
    System.out.println("Token: "+ token);
    return token;

  } 
}