package com.example.jwt.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.*;

@Component
public class JwtUtil {
	
	private final String SECRET ="superscretkeysupersecretkey123superscretkey";
	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	private final long ACCESS_EXP = 1000 * 60 * 10;   //10min
	private final long REFRESH_EXP = 1000 * 60 * 24;   //1 day
	
	public String generateAccessToken(String username) {
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
				.signWith(key)
				.compact();
	}
	public String generateRefreshToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() +REFRESH_EXP))
				.signWith(key)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()   //JWT read panna parser object create pannudhu.
				.setSigningKey(key) 
				.build()//Token sign panna use pannina same SECRET key kudukanum.
				.parseClaimsJws(token)
				.getBody()
				.getSubject();   //Subject (sub) claim return pannum.
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	

}
