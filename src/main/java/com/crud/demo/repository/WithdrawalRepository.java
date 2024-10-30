package com.crud.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.crud.demo.model.Withdrawal;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
