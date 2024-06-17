package com.example.dev2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev2.Dto.OrderRequestDto;
import com.example.dev2.Dto.ProductRequestDTO;
import com.example.dev2.entity.OrderDetails;
import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;
import com.example.dev2.service.AdminService;
import com.example.dev2.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.transaction.Transactional;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api/user" , headers="Accept=application/json")
public class CustomerController {
	
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("products")
	public List<ProductEntity> getAllProducts(ProductEntity product) {
		
		return customerService.getAllProducts(product);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("products/{productcategoryId}")
	public List<ProductEntity> getProductsByCategoryId(@PathVariable("productcategoryId") Integer productcategoryId){
		
		return customerService.getProductsByCategoryId(productcategoryId);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("product/{id}")
	public Optional<ProductEntity> getProductById(@PathVariable("id")Integer id){
		return customerService.getProductById(id);
		
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("trending")
	public List<ProductEntity> getTrending(){
		return customerService.getTrending();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("placeOrder/{customerId}")
	public ResponseEntity<OrdersEntity> placeOrder(@RequestBody OrderRequestDto orderRequest) {
		try {
			OrdersEntity order = customerService.placeOrder(orderRequest.getCustomerId(), orderRequest.getOrderDetails());
			return ResponseEntity.ok(order);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
