package com.example.dev2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
	
	

}
