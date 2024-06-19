package com.example.dev2.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name ="customer_id",nullable = false)
	@JsonIgnore
	private CustomerEntity customer;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<CartItem> cartItem;
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Cart(Integer id, CustomerEntity customer, Set<CartItem> cartItem) {
		super();
		this.id = id;
		this.customer = customer;
		this.cartItem = cartItem;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public CustomerEntity getCustomer() {
		return customer;
	}



	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}



	public Set<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Set<CartItem> cartItem) {
		this.cartItem = cartItem;
	}
	
	

}
