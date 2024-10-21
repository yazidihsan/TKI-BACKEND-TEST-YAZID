package com.crud.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.Withdrawal;

public interface WithdrawalRepository extends MongoRepository<Withdrawal, String> {
}
