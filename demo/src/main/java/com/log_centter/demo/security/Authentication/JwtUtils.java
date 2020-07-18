package com.log_centter.demo.security.Authentication;

import com.log_centter.demo.security.key.GenerateRSA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtils {
  
  @Autowired
  GenerateRSA rsaKey;

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  private final String encodedPublicKey = rsaKey.getEncodedPublicKey();

  @Value("${jwt.expirationTimeInMS}")
  private Long jwtExpirationTime;

  public String generateJwtToken(){ return null;}
  
}