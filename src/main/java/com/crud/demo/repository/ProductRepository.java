package com.crud.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String>{
    
        List<Product> findByCategory_Id(String categoryId);
}   
