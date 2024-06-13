package com.example.dev2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.OrdersEntity;
import com.example.dev2.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

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