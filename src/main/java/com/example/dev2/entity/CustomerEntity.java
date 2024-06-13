package com.example.dev2.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    
    private String name;
    private String emailId;
    private String password;
    
    private String mobile;
    private String address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersEntity> orders;

	public CustomerEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public CustomerEntity(Integer customerId, String name, String emailId, String password, String mobile,
			String address, List<OrdersEntity> orders) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.mobile = mobile;
		this.address = address;
		this.orders = orders;
	}



	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

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

	public List<OrdersEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrdersEntity> orders) {
		this.orders = orders;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}
	
	
    
    
}
