package com.example.dev2.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.dev2.Dto.CustomerDTO;
import com.example.dev2.Dto.ProductRequestDTO;
import com.example.dev2.entity.CustomerEntity;
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
	@Autowired
	private OrdersRepository ordersRepo;
	
	
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
	
	
//	public OrderDetails PlaceOrderById(Integer quantity, Double unitPrice, Double totalPrice, OrdersEntity order, Integer productId) {
//			
//		
//		Optional<ProductEntity> prod = productRepo.findById(productId);
//
//        if (!prod.isPresent()) {
//            throw new RuntimeException("product not found");
//        }
//			ProductEntity product = prod.get();
//			OrderDetails placeOrder = new OrderDetails();
//			placeOrder.setQuantity(quantity);
//			placeOrder.setUnitPrice(unitPrice);
//			placeOrder.setTotalPrice(unitPrice * quantity);
//			placeOrder.setOrder(order);
//			placeOrder.setProduct(product);
//				
//			return orderDetailsRepo.save(placeOrder);
//			
//			
//		}
			
		
	public OrdersEntity placeOrder(Integer customerId, List<OrderDetails> orderDetails) throws Exception{
		
		Optional<CustomerEntity> customer = customerRepo.findById(customerId);
		if(!customer.isPresent()) {
			throw new Exception("Customer not found");
			
		}
		
		CustomerEntity cust = customer.get();
		OrdersEntity order = new OrdersEntity();
		order.setCustomer(cust);
		order.setOrderDate(new Date());
		order.setStatus("Pending");
		
		double totalAmount = 0;
		
		for(OrderDetails ordList : orderDetails) {
			 Optional<ProductEntity> productOptional = productRepo.findById((ordList.getProduct().getId()));
	            if (!productOptional.isPresent()) {
	                throw new Exception("Product not found for ID: " + (ordList.getProduct().getId()));
	            }
	            
	         ProductEntity product = productOptional.get();
	         ordList.setOrder(order);
	         ordList.setUnitPrice(product.getAmount());
	         ordList.setTotalPrice(product.getAmount() * ordList.getQuantity());
	         
	         totalAmount += ordList.getTotalPrice();
		}
		
		order.setTotalAmount(totalAmount);
		
		OrdersEntity saveOrder = ordersRepo.save(order);
		
		for(OrderDetails ordList : orderDetails) {
			orderDetailsRepo.save(ordList);
		}
		
		return saveOrder;
		
	}
		
	
	
	
	

}
