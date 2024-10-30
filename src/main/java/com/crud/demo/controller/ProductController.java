package com.crud.demo.controller;

import java.util.List;

import com.crud.demo.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.crud.demo.dto.ProductResponse;
import com.crud.demo.model.Product;

import com.crud.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("/get-all-products")
    public ResponseEntity<?> getProducts(@RequestHeader(value = "Authorization") String token) {
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }

        try{
            if(token.isEmpty()){
                return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }
            List<Product> product = productService.getAllProducts();

            return ResponseEntity.status(HttpStatus.OK).body(new ProductResponse(200,"get all products successfully",product));
        }catch (RuntimeException re){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(400,"Bad Request",re.getMessage()));
        }

        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponse(500,"Internal Servel Error",e.getMessage()));
        }
    }

    @PostMapping("/create-product")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request, @RequestHeader(value = "Authorization") String token) {
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        try {
            if (token.isEmpty()) {
                return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }
            Product product  = productService.createProduct(request.getName(),request.getDescription(),request.getPrice(),request.getCategory());

            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(201,"Product created successfully",product));
        }
        catch (RuntimeException re){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponse(400,"Bad Request",re.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponse(500,"Internal Server Error",e.getMessage()));
        }
    }
    
}
