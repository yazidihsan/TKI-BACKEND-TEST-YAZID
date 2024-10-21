

 package com.crud.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.Transaction;

import java.util.Optional;

public interface TransactionRepository extends MongoRepository<Transaction,String> {

    Optional<Transaction> findFirstByKodeStartingWithOrderByKodeDesc(String yymm);
}