package com.crud.demo.model;

import jakarta.persistence.*;
//import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;

//@Document(collection = "transactions")
@Entity
@Table(name = "transactions")
public class Transaction {
//    @Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kode;
    private LocalDateTime transactionDate;
    @Column(name = "user_id",nullable = false)
    private String userId;
    private String keterangan;
    private BigDecimal amount;
    private BigDecimal admin;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private String createdBy;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public String getKode() {
        return kode;
    }
    public void setKode(String kode) {
        this.kode = kode;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getKeterangan() {
        return keterangan;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAdmin() {
        return admin;
    }
    public void setAdmin(BigDecimal admin) {
        this.admin = admin;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


}
