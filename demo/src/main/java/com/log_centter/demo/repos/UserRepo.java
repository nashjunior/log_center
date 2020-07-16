package com.log_centter.demo.repos;

import com.log_centter.demo.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

	User findByEmail(String email);
  
}