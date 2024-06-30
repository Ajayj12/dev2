package com.example.dev2.Dto;

import java.util.List;

import com.example.dev2.entity.CartItem;
import com.example.dev2.entity.OrderDetails;

public class OrderRequestDto {
	
	private Integer customerId;
	
	private List<OrderDetails> orderDetails;
	
	private List<CartItem> cartItems;
	

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	
	
	
	

}
