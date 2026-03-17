package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.BlacklistedToken;

public interface BlacklistRepository extends JpaRepository<BlacklistedToken, Long> {
	
	boolean existsByToken(String token);

}
