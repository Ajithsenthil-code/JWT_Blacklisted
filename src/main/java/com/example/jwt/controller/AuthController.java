package com.example.jwt.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.dto.AuthRequest;
import com.example.jwt.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
//@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService=authService;
	}
	@PostMapping("/register")
	public void register(@RequestBody AuthRequest request) {
		authService.register(request.getUsername(), request.getPassword());
//		return "dskjf";
		
	}
	
	@PostMapping("/login")
	public Map<String, String > login(@RequestBody AuthRequest request){
									
		return authService.login(request.getUsername(),request.getPassword());
	}
	
	//Client refresh token anupum
	// Service new access token generate pannum
	@PostMapping("/refresh")
	public Map<String, String> refresh(@RequestParam String refreshToken){
		String newAccess = authService.refresh(refreshToken);
		
		return Map.of("accessTOken" ,newAccess);
		
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		
		String header = request.getHeader("Authorization");
		String token = header.substring(7);
		
		authService.logout(token);
		
		return "Logged out and token blacklisyted";
	}
	
	@GetMapping("/secure/profile")
	public String profile() {
		return "Secure Profile Data";
	}
	
	@GetMapping("/secure/orders")
	public String orders() {
		return "Secure Orders Data";
	}
	
	@GetMapping("/secure/settings")
	public String settings() {
		return "Secure Settings Data";
	}
	
	

}
