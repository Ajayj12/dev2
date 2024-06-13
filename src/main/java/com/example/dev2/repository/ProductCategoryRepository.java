package com.example.dev2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductCategory;
import com.example.dev2.entity.ProductEntity;
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{

	
	
	

}
