package com.log_centter.demo.repos;

import com.log_centter.demo.entities.Log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends JpaRepository <Log, Long> {
}