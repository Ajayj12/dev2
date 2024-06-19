package com.example.dev2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.Cart;
import com.example.dev2.entity.CustomerEntity;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	Optional<Cart> findByCustomer(CustomerEntity customer);

}
