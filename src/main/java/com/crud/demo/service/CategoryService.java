package com.crud.demo.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.model.Category;
import com.crud.demo.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();

    }

    public Category createCategory(String name, String description){

     

        Category category = new Category();

        Category existingCategory = categoryRepository.findByName(name);
        if(existingCategory != null){
             throw new RuntimeException("Category's name already exists");
        }
        category.setName(name);
        category.setDescription(description);
        
        return categoryRepository.save(category);
    }
    


}
