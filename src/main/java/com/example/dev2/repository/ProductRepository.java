package com.example.dev2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	List<ProductEntity> findByStockLessThanEqual(int stockleft);
	
	@Query("SELECT p FROM ProductEntity p WHERE p.productCategory.id = :productcategoryId")
	List<ProductEntity> findProductsByCategory_Id(Integer productcategoryId);

}


//<dependency>
//<groupId>org.springframework.security</groupId>
//<artifactId>spring-security-test</artifactId>
//<scope>test</scope>
//</dependency>

//<dependency>
//<groupId>org.springframework.boot</groupId>
//<artifactId>spring-boot-starter-security</artifactId>
//</dependency>