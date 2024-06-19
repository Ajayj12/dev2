package com.example.dev2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "product_id",nullable = false)
	private ProductEntity product;
	
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "cart_id",nullable = false)
	private Cart cart;

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public CartItem(Integer id, ProductEntity product, Integer quantity, Cart cart) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.cart = cart;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public ProductEntity getProduct() {
		return product;
	}



	public void setProduct(ProductEntity product) {
		this.product = product;
	}



	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	
	

}
