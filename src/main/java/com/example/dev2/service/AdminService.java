package com.example.dev2.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

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
public class AdminService {
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
	
	
	
	public ProductEntity addProduct( String name, Double amount,String image, Integer stock, Integer categoryId) {

		Optional<ProductCategory> categoryOptional = productCatRepo.findById(categoryId);

        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found");
        }

        ProductCategory category = categoryOptional.get();
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setAmount(amount);
        product.setImage(image);
        product.setStock(stock);
        product.setProductCategory(category);

        return productRepo.save(product);

	}
	
	public ProductEntity updateProduct(Integer id, Double amount,String image, Integer stock) {
		
		Optional<ProductEntity> pro = productRepo.findById(id); 
		if(pro.isPresent()) {
			ProductEntity p = pro.get();
			System.out.println(amount);
			p.setAmount(amount);
			p.setImage(image);
			p.setStock(stock);
			
			System.out.println(p.getAmount());
			
			productRepo.save(p);
			return p;
		}
		return null;
		
	}
	
	public ProductEntity deleteProduct(Integer id) {
		Optional<ProductEntity> pro = productRepo.findById(id);
		if(pro.isPresent()) {
			ProductEntity p = pro.get();
			productRepo.delete(p);
			return p;
		}
		return null;
	}
	
	
	public List<OrdersEntity> getAllOrders(OrdersEntity orders) {
		List<OrdersEntity> ord = ordersRepo.findAll();
		return ord;
	}
	
	
	
	public OrdersEntity acceptOrder(Integer orderId,List<OrderDetails> orderDetails) throws Exception {
		
		Optional<OrdersEntity> ord = ordersRepo.findById(orderId);
		
		if(!ord.isPresent()) {
			throw new Exception("Order not found");
		}
		
		OrdersEntity order = ord.get();
		order.setStatus("Order Accepted");
		
		for(OrderDetails ordList : orderDetails) {
			 Optional<ProductEntity> productOptional = productRepo.findById((ordList.getProduct().getId()));
	            if (!productOptional.isPresent()) {
	                throw new Exception("Product not found for ID: " + (ordList.getProduct().getId()));
	            }
	            
	            ProductEntity prod = productOptional.get();
	            prod.setStock(prod.getStock() - ordList.getQuantity());
	            productRepo.save(prod);

		ordersRepo.save(order);
		
		
	}
	
		return order;
	
	
	
	
	}
	
	
	
	

}
