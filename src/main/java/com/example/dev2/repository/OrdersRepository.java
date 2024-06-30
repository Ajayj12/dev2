package com.example.dev2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.OrderDetails;
import com.example.dev2.entity.OrdersEntity;
@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Integer>{

	

	List<OrdersEntity> findByCustomerCustomerId(Integer customerId);

}
