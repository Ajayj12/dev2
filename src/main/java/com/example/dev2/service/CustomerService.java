package com.example.dev2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.dev2.Dto.CustomerDTO;
import com.example.dev2.Dto.ProductRequestDTO;
import com.example.dev2.entity.OrderDetails;
import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.repository.CustomerRepository;
import com.example.dev2.repository.OrderDetailsRepository;
import com.example.dev2.repository.OrdersRepository;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private OrderDetailsRepository orderDetailsRepo;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	
	public List<ProductEntity> getAllProducts(ProductEntity product) {
		
		List<ProductEntity> prod = productRepo.findAll();
		return prod;
		
	}
	
	public Optional<ProductEntity> getProductById(Integer id) {
		return productRepo.findById(id);
	}
	
	public Optional<ProductCategory> getProductsByCategory(Integer categoryId){
		return productCatRepo.findById(categoryId);
	}
	
	
	public OrderDetails PlaceOrderById(Integer quantity, Double unitPrice, Double totalPrice, OrdersEntity order, Integer productId) {
			
		
		Optional<ProductEntity> prod = productRepo.findById(productId);

        if (!prod.isPresent()) {
            throw new RuntimeException("product not found");
        }
			ProductEntity product = prod.get();
			OrderDetails placeOrder = new OrderDetails();
			placeOrder.setQuantity(quantity);
			placeOrder.setUnitPrice(unitPrice);
			placeOrder.setTotalPrice(unitPrice * quantity);
			placeOrder.setOrder(order);
			placeOrder.setProduct(product);
				
			return orderDetailsRepo.save(placeOrder);
			
			
		}
			
		
		
	
	
	
	

}
