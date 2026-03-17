package com.example.jwt.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwt.repository.BlacklistRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	//“Secure API ku varra request valid user ah illa fake ah?” nu check pannradhu.
	
	private final JwtUtil jwtUtil;
	private final BlacklistRepository blacklistRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			        				HttpServletResponse response,
			        				FilterChain chain)
	throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		
		//If header illa OR correct format ila-> skip
		if(header !=null && header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
			String token = header.substring(7);
			
			//Blacklist Check
			if(blacklistRepository.existsByToken(token)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			
			//validate token
			if(jwtUtil.validateToken(token)) {
				String username = jwtUtil.extractUsername(token);
				
				
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, null);
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		
			chain.doFilter(request, response);
		}
	}
	
	


