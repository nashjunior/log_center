package com.log_centter.demo.repos;

import java.util.Optional;

import com.log_centter.demo.entities.Log;
import com.log_centter.demo.entities.LogLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends JpaRepository <Log, Long> {
  Optional <Log> findById(Long id);

  Long countByLevel(LogLevel level);
}