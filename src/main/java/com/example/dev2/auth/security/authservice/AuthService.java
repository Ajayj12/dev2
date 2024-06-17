package com.example.dev2.auth.security.authservice;

import com.example.dev2.auth.security.dto.JwtAuthResponse;
import com.example.dev2.auth.security.dto.LoginDto;
import com.example.dev2.auth.security.dto.RegisterDto;

public interface AuthService {
	public JwtAuthResponse login(LoginDto loginDto);
	public String register(RegisterDto registerDto);
}
