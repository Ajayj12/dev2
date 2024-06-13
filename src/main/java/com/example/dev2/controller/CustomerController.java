package com.example.dev2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;
import com.example.dev2.service.AdminService;
import com.example.dev2.service.CustomerService;

@RestController
@RequestMapping(path="/api/user" , headers="Accept=application/json")
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	@GetMapping("products")
	public List<ProductEntity> getAllProducts(ProductEntity product) {
		
		return customerService.getAllProducts(product);
	}
	
	@GetMapping("products/{categoryId}")
	public Optional<ProductCategory> getProductsByCategory(@PathVariable("categoryId")Integer categoryId){
		
		return customerService.getProductsByCategory(categoryId);
	}
	
	@GetMapping("product/{id}")
	public Optional<ProductEntity> getProductById(@PathVariable("id")Integer id){
		return customerService.getProductById(id);
		
	}

}
