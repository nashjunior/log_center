package com.log_centter.demo.repos;

import java.util.Optional;

import com.log_centter.demo.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);
  
}