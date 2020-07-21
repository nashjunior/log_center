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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

  private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  private PublicKey publicKey;
  private PrivateKey privateKey;

  public PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
    return this.privateKey;
  }

  private String encodedPublicKey;

  public String getEncodedPublicKey() throws NoSuchAlgorithmException {
    return this.encodedPublicKey;
  }

  @Value("${jwt.expirationTimeInMS}")
  private int jwtExpirationTime;

  @SuppressWarnings("deprecation")
  public String generateJwtToken(Authentication authentication) throws NoSuchAlgorithmException {
    this.generateRsa();
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    String token = Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtExpirationTime))
        .claim("groups", userPrincipal.getAuthorities()).signWith(SignatureAlgorithm.RS256, this.privateKey).compact();
    System.out.println("Token: " + token);
    return token;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(this.encodedPublicKey).parseClaimsJws(token).getBody().getSubject();
  }

  @SuppressWarnings("deprecation")
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(this.encodedPublicKey).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  private final void generateRsa() throws NoSuchAlgorithmException {
    KeyPair kp;
    if (this.publicKey == null && this.privateKey == null) {
      KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
      keyGenerator.initialize(2048);

      kp = keyGenerator.genKeyPair();
      this.publicKey = (PublicKey) kp.getPublic();
      this.privateKey = (PrivateKey) kp.getPrivate();
      this.encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
  }
}