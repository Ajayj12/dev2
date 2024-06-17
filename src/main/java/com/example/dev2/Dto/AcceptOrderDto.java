package com.example.dev2.Dto;

import java.util.List;

import com.example.dev2.entity.OrderDetails;

public class AcceptOrderDto {
	
	private Integer orderId;
	
	private List<OrderDetails> orderDetails;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	

}
