package com.crud.demo.repository;


 

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.Category; 

public interface CategoryRepository extends MongoRepository<Category,String> {

 
     Category findByName(String name);
} 
