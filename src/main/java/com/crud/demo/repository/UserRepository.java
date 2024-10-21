package com.crud.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.User;
import java.util.List;

 
 
   


public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByRole(String role);
    Optional<User> findById(String id);
}
