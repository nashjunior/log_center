package com.log_centter.demo.security.authentication.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

import com.log_centter.demo.security.authentication.user_details.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

@Component
public class JwtUtils {

  private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  private PublicKey publicKey;
  private PrivateKey privateKey;

  public PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
    this.generateRsa();
    return this.privateKey;
  }

  private String encodedPublicKey;

  public String getEncodedPublicKey() throws NoSuchAlgorithmException {
    this.generateRsa();

    System.out.println(this.encodedPublicKey);
    return this.encodedPublicKey;
  }

  
    /* @Value("${jwt.expirationTimeInMS}") private int jwtExpirationTime;
    
    @SuppressWarnings("deprecation") public String
    generateJwtToken(Authentication authentication){ UserDetailsImpl
    userPrincipal = (UserDetailsImpl) authentication.getPrincipal(); String token
    = Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new
    Date()) .setExpiration(new Date(new Date().getTime() + jwtExpirationTime)
    ).claim("groups", userPrincipal.getAuthorities())
    .signWith(SignatureAlgorithm.HS512, privateKey).compact();
    System.out.println("Token: "+ token); return token;
    
    } */
  

  private final void generateRsa() throws NoSuchAlgorithmException {
    KeyPair kp;
    if(this.publicKey==null && this.privateKey==null) {
      KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
      keyGenerator.initialize(1024);
  
      kp = keyGenerator.genKeyPair();
      this.publicKey = (PublicKey) kp.getPublic();
      this.privateKey = (PrivateKey) kp.getPrivate();
      this.encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
  } 
}