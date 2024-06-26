package com.example.dev2.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dev2.Dto.AcceptOrderDto;
import com.example.dev2.Dto.ProductRequestDTO;
import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;
import com.example.dev2.service.AdminService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path="/api/admin" , headers="Accept=application/json")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	
	@PostMapping("addproduct")
	public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductRequestDTO productDto ) throws NotFoundException {
//		ProductCategory productcategory = productCatRepo.findById(categoryId).orElseThrow(() -> new NotFoundException());
		
		
		ProductEntity pro = adminService.addProduct(productDto.getName(),productDto.getAmount(),productDto.getImage(), productDto.getStock(),productDto.getCategoryId());
		
		return ResponseEntity.ok(pro); 
		
	}
	
	@PutMapping("update")
	public ProductEntity updateProduct(@RequestBody ProductRequestDTO productDto) {
		ProductEntity pro = adminService.updateProduct(productDto.getId(),productDto.getAmount(),productDto.getImage(), productDto.getStock());
		return pro;
	}
	
	
	@DeleteMapping("delete/{id}")
	public ProductEntity deleteProduct(@PathVariable("id")Integer id) {
		ProductEntity pro = adminService.deleteProduct(id);
		return pro;
	}
	
	@PutMapping("accept")
	public ResponseEntity<OrdersEntity> acceptOrder(@RequestBody AcceptOrderDto acceptOrd) {
		try {
			OrdersEntity order = adminService.acceptOrder(acceptOrd.getOrderId(), acceptOrd.getOrderDetails());
			return ResponseEntity.ok(order);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
		
	}
	
	
}
