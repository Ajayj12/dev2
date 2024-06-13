package com.example.dev2.service;

import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.dev2.entity.CustomerEntity;
import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.repository.ProductCategoryRepository;
import com.example.dev2.repository.ProductRepository;

@Service
public class AdminService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	
	
	public ProductEntity addProduct( String name, Double amount, Integer stock, Integer categoryId) {

		Optional<ProductCategory> categoryOptional = productCatRepo.findById(categoryId);

        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category not found");
        }

        ProductCategory category = categoryOptional.get();
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setAmount(amount);
        product.setStock(stock);
        product.setProductCategory(category);

        return productRepository.save(product);

	}
	
	public ProductEntity updateProduct(Integer id, Double amount, Integer stock) {
		
		Optional<ProductEntity> pro = productRepository.findById(id); 
		if(pro.isPresent()) {
			ProductEntity p = pro.get();
			System.out.println(amount);
			p.setAmount(amount);
			p.setStock(stock);
			
			System.out.println(p.getAmount());
			
			productRepository.save(p);
			return p;
		}
		return null;
		
	}
	
	public ProductEntity deleteProduct(Integer id) {
		Optional<ProductEntity> pro = productRepository.findById(id);
		if(pro.isPresent()) {
			ProductEntity p = pro.get();
			productRepository.delete(p);
			return p;
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
