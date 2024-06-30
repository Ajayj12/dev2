package com.example.dev2.Dto;

import com.example.dev2.entity.CustomerEntity;

public class RemoveItemDto {
	
	private Integer customerId;
	
	private Integer productId;

	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
}
