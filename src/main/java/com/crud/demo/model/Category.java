package com.crud.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
//    private List<Product> products;

    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
//    public List<Product> getProducts() {
//        return products;
//    }
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }
    
}
