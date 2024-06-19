package com.example.dev2.Dto;

import com.example.dev2.entity.ProductEntity;

public class AddToCartDto {
	
	private Integer customerId;
	
	private ProductEntity product;
	
	private Integer quantity;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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
	
	

}
