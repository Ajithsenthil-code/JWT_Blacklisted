package com.example.jwt.service;

import java.util.Date;
import java.util.Map;

//import java.beans.Encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.BlacklistedToken;
import com.example.jwt.entity.User;
import com.example.jwt.repository.BlacklistRepository;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	
	private final UserRepository userRepo;                                                                
	
	private final BlacklistRepository blacklistRepo;
	
	
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	
	public void register(String username, String password) {
		User user= new User();
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		userRepo.save(user);
		System.out.println("hi");
		
	}
	public Map<String, String> login(String username, String password){
		
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		if(!encoder.matches(password, user.getPassword()))
			throw new RuntimeException("Invalid credentials");
		
		String access = jwtUtil.generateAccessToken(username);
		String refresh = jwtUtil.generateRefreshToken(username);
		
		user.setRefreshToken(refresh);
		userRepo.save(user);
		
		return Map.of(
				"accessToken", access,
				"refreshToken", refresh 
				);
	}
	
 
	
	
	public String refresh(String refreshToken) {
		
		String username = jwtUtil.extractUsername(refreshToken);
		
		User user = userRepo.findByUsername(username)
				.orElseThrow();
		if(!refreshToken.equals(user.getRefreshToken()))
			throw new RuntimeException("Invalid refresh token");
		
		System.out.println("wad");
		
		return jwtUtil.generateAccessToken(username);
	}
	
	
	public void logout(String token) {
		 
		BlacklistedToken blacklisted = new BlacklistedToken();
		System.out.println("Hello");
		blacklisted.setToken(token);
		blacklisted.setBlacklistedAt(new Date());
		
		blacklistRepo.save(blacklisted);
		
		
	}
	
	

}
