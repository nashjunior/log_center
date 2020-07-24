package com.log_centter.demo.services.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericInterface<T> {
  List<T> findAll();
  T save(T object);

}