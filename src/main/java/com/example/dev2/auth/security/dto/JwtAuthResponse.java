package com.example.dev2.auth.security.dto;

public class JwtAuthResponse {
	private String accessToken;
	private String tokenType="Bearer ";
	private Boolean auth = false;
	private String emailId;
	private Integer id;
	

	
	
	
	
	public JwtAuthResponse(String accessToken, String tokenType, Boolean auth, String emailId, Integer id) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.auth = auth;
		this.emailId = emailId;
		this.id = id;
	}
	public Boolean getAuth() {
		return auth;
	}
	public void setAuth(Boolean auth) {
		this.auth = auth;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public JwtAuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}	
