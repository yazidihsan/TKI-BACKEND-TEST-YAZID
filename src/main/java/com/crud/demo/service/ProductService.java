package com.crud.demo.service;

import com.crud.demo.model.Category;
import com.crud.demo.repository.CategoryRepository;
import com.crud.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.crud.demo.model.Product;
import com.crud.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private JwtUtil jwtUtil;


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product createProduct(String name, String description, BigDecimal price, Category category){
        Product product = new Product();

        product.setName(name);

        Category existingCategory = categoryRepository.findById(category.getId()).orElseThrow();

        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(existingCategory);
        
        return productRepository.save(product);
    }
}
