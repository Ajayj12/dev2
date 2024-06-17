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
	
	public List<ProductEntity> getProductsByCategoryId(Integer productcategoryId){
		
		return productRepo.findProductsByCategory_Id(productcategoryId);
	}
	
	public List<ProductEntity> getTrending() {
		
		int stockleft = 95;
        return productRepo.findByStockLessThanEqual(stockleft);
		
	}
	
		
		
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
	         if(product.getStock() != 0) {
	         ordList.setOrder(order);
	         ordList.setUnitPrice(product.getAmount());
	         ordList.setTotalPrice(product.getAmount() * ordList.getQuantity());
	         
	         totalAmount += ordList.getTotalPrice();
	         }
		}
		
		order.setTotalAmount(totalAmount);
		
		OrdersEntity saveOrder = ordersRepo.save(order);
		
		for(OrderDetails ordList : orderDetails) {
			orderDetailsRepo.save(ordList);
		}
		
		return saveOrder;
		
	}
		
	
	
	
	

}
