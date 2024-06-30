package com.example.dev2.controller;

import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev2.Dto.AcceptOrderDto;
import com.example.dev2.Dto.OrderRequestDto;
import com.example.dev2.Dto.ProductRequestDTO;
import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;
import com.example.dev2.service.AdminService;
import com.example.dev2.service.CustomerService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api/admin" , headers="Accept=application/json")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("addproduct")
	public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductRequestDTO productDto ) throws NotFoundException {

		
		ProductEntity pro = adminService.addProduct(productDto.getName(),productDto.getAmount(),productDto.getImage(), productDto.getStock(),productDto.getCategoryId());
		
		return ResponseEntity.ok(pro); 
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("update")
	public ProductEntity updateProduct(@RequestBody ProductRequestDTO productDto) {
		ProductEntity pro = adminService.updateProduct(productDto.getId(),productDto.getAmount(),productDto.getImage(), productDto.getStock());
		return pro;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("delete/{id}")
	public ProductEntity deleteProduct(@PathVariable("id")Integer id) {
		ProductEntity pro = adminService.deleteProduct(id);
		return pro;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("accept")
	public ResponseEntity<OrdersEntity> acceptOrder(@RequestBody AcceptOrderDto acceptOrd) {
		try {
			OrdersEntity order = adminService.acceptOrder(acceptOrd.getOrderId(), acceptOrd.getOrderDetails());
			return ResponseEntity.ok(order);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
		
	}
	
	
	
	//APIS USED BY USER AND ALSO ADMIN
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("products")
	public List<ProductEntity> getAllProducts(ProductEntity product) {
		
		return customerService.getAllProducts(product);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("products/{productcategoryId}")
	public List<ProductEntity> getProductsByCategoryId(@PathVariable("productcategoryId") Integer productcategoryId){
		
		return customerService.getProductsByCategoryId(productcategoryId);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("product/{id}")
	public Optional<ProductEntity> getProductById(@PathVariable("id")Integer id){
		return customerService.getProductById(id);
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("trending")
	public List<ProductEntity> getTrending(){
		return customerService.getTrending();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("placeOrder/{customerId}")
	public ResponseEntity<OrdersEntity> placeOrder(@RequestBody OrderRequestDto orderRequest) {
		try {
			OrdersEntity order = customerService.placeOrder(orderRequest.getCustomerId(), orderRequest.getOrderDetails(), orderRequest.getCartItems());
			return ResponseEntity.ok(order);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	
}
