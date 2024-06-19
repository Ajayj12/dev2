package com.example.dev2.entity;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class WishList {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "customerId")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

	public WishList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WishList(Integer id, CustomerEntity customer, ProductEntity product) {
		super();
		this.id = id;
		this.customer = customer;
		this.product = product;
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

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}
    
	
    

}
