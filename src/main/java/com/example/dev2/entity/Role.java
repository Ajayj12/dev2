package com.example.dev2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

	public Role() {
		
	}
	
	
	public Role(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
    
    

}
