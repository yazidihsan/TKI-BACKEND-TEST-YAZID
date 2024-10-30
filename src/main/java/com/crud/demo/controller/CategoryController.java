package com.crud.demo.controller;

import java.net.http.HttpRequest;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.dto.CategoryRequest;
import com.crud.demo.dto.CategoryResponse;
import com.crud.demo.model.Category;
import com.crud.demo.service.CategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api")
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;


    @GetMapping("/get-all-categories")
    public ResponseEntity<?> getAllCategories(@RequestHeader(value = "Authorization") String token) {

        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        try {
            if ( token.isEmpty()) {
                return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }

            List<Category> categories = categoryService.getAllCategories();

            return ResponseEntity.status(HttpStatus.OK).body(new CategoryResponse(200,"Success get all categories",categories));

        }catch(RuntimeException re){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CategoryResponse(400,"Bad Request",null));
        }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CategoryResponse(500, "Internal Server Error", null));
        }
         
    }

    @PostMapping("/create-category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request, @RequestHeader(value = "Authorization") String token) {
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        try {
            if ( token.isEmpty()) {
                return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }

            Category category = categoryService.createCategory(request.getName(),request.getDescription());

            return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryResponse(201, "category is created", category));

        }catch(RuntimeException re){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CategoryResponse(400, "bad request", null));
        } 
        
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CategoryResponse(500, "Internal Server Error", null));
        }

        
      
    }
    
    



}
