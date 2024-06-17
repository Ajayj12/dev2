package com.example.dev2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dev2.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{
	Optional<Role> findByName(String name);

}
