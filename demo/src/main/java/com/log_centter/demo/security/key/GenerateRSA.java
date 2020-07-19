package com.log_centter.demo.security.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GenerateRSA {
  private String encodedPublicKey;
  private PrivateKey privateKey;

  public PrivateKey getPrivateKey() {
    return this.privateKey;
  }

  public String getEncodedPublicKey() {
    return this.encodedPublicKey;
  }

  public GenerateRSA () throws NoSuchAlgorithmException {
    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
    keyGenerator.initialize(1024);

		KeyPair kp = keyGenerator.genKeyPair();
    PublicKey publicKey = (PublicKey) kp.getPublic();
    this.privateKey = (PrivateKey) kp.getPrivate();
    this.encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
  }
}