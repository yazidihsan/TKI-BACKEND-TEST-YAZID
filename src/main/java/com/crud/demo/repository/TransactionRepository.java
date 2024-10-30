

 package com.crud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(String userId);
    Optional<Transaction> findFirstByKodeStartingWithOrderByKodeDesc(String yymm);
}