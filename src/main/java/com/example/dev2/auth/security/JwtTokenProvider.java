package com.example.dev2.auth.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationTime;
	
	// Generate Jwt Token
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime()+jwtExpirationTime);
		String token = Jwts.builder()
									.setSubject(username)
									.setIssuedAt(currentDate)
									.setExpiration(expirationDate)
									.signWith(key())
									.compact();
		return token;
	}
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	// retrieve username from Jwt token
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
												.setSigningKey(key())
												.build()
												.parseClaimsJws(token)
												.getBody();
	
		return claims.getSubject();
	}
	
	// validate Jwt Token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key())
					.build()
					.parse(token);
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
