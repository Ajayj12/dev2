package com.example.dev2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.CustomerEntity;
import com.example.dev2.entity.ProductEntity;
import com.example.dev2.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

	Optional<WishList> findByCustomerCustomerId(Integer customerId);

	Optional<WishList> findByCustomerAndProduct(CustomerEntity customerEntity, ProductEntity productEntity);

	List<WishList> findByCustomer(CustomerEntity customer);

	


}
