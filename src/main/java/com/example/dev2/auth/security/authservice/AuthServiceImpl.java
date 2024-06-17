package com.example.dev2.auth.security.authservice;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dev2.auth.security.JwtTokenProvider;
import com.example.dev2.auth.security.dto.JwtAuthResponse;
import com.example.dev2.auth.security.dto.LoginDto;
import com.example.dev2.auth.security.dto.RegisterDto;
import com.example.dev2.entity.CustomerEntity;
import com.example.dev2.entity.Role;
import com.example.dev2.repository.CustomerRepository;
import com.example.dev2.repository.RoleRepository;
import org.modelmapper.ModelMapper;



@Service
public class AuthServiceImpl implements AuthService {
	private AuthenticationManager autheticationManager;
	@Autowired
	private CustomerRepository customerRepository;
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
	
	
	
	@Autowired
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	private ModelMapper mapper;

	
	

	public AuthServiceImpl(AuthenticationManager autheticationManager, CustomerRepository customerRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
			ModelMapper mapper) {
		super();
		this.autheticationManager = autheticationManager;
		this.customerRepository = customerRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.mapper = mapper;
	}

	@Override
	public JwtAuthResponse login(LoginDto loginDto) {
		
		// get Authentication object from AuthenticationManager 
		Authentication authentication = autheticationManager.authenticate(new UsernamePasswordAuthenticationToken( 
				loginDto.getEmailId(),loginDto.getPassword())
		);
		
		
		
		// store authentication in spring context holder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
//		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getEmailId());
//		String empId = userDetails.getUsername();
//		System.out.println(empId);
		 CustomerEntity temp = customerRepository.findByEmailId(loginDto.getEmailId());
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setAccessToken(token);
		authResponse.setEmailId(loginDto.getEmailId());
		authResponse.setId(temp.getCustomerId());
		authResponse.setAuth(true);
	 return authResponse;
	}

	@Override
	public String register(RegisterDto registerDto) {
		if(customerRepository.existsByEmailId(registerDto.getEmailId())) {
//			throw new Exception("Email already exists");
			return "Email Already Exists";
		}
		
		CustomerEntity user = new CustomerEntity();
		user.setName(registerDto.getName());
		user.setEmailId(registerDto.getEmailId());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setMobile(registerDto.getMobile());
		
	
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		customerRepository.save(user);
		return "User Registered Successfully..!";
		
	}



}