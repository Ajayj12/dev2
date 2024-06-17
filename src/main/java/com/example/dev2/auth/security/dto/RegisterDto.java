package com.example.dev2.auth.security.dto;

public class RegisterDto {
	
	private String name;
	private String emailId;
	private String password;
	private String mobile;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	
	public RegisterDto(String name, String emailId, String password, String mobile) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.mobile = mobile;
	}
	public RegisterDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
