package com.example.dev2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.Cart;
import com.example.dev2.entity.CartItem;
import com.example.dev2.entity.ProductEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	Optional<CartItem> findByCartAndProduct(Cart cart, ProductEntity product);

	List<CartItem> findByCart(Cart cart);



}
