package com.example.dev2.auth.security.authservice;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dev2.entity.CustomerEntity;
import com.example.dev2.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	public CustomUserDetailsService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		try {
			CustomerEntity user = customerRepository.findByEmailId(emailId);
		System.out.println("User in DB  : " + user);

			Set<GrantedAuthority> authorities = user.getRoles().stream()
					.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

			return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
					authorities);
			
			

		} catch (Exception e) {
			throw new UsernameNotFoundException("User Not Found with email : " + emailId);

		}

	}
}
