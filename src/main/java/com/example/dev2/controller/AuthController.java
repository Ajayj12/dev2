package com.example.dev2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev2.auth.security.authservice.AuthService;
import com.example.dev2.auth.security.dto.JwtAuthResponse;
import com.example.dev2.auth.security.dto.LoginDto;
import com.example.dev2.auth.security.dto.RegisterDto;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    //Login Rest Api
    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        JwtAuthResponse authResponse = authService.login(loginDto);
        return ResponseEntity.ok(authResponse);
    }
    
    //Build Register rest api
    @PostMapping(value= {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws Exception{
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}