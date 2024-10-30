package com.crud.demo.model;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;


//@Document(collection = "withdrawals")
@Entity
@Table(name = "withdrawals")
public class Withdrawal {
//    @Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionCode; // Similar to TopUp transaction code
    @Column(name = "user_id",nullable = false)
    private String userId; // ID of the user making the withdrawal
    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private String keterangan;      // Description of the withdrawal
    private BigDecimal saldoBefore; // User's balance before the withdrawal
    private BigDecimal saldoAfter;  // User's balance after the withdrawal

    private String createdBy; // The user who initiated the withdrawal

    // public Withdrawal(String id, String transactionCode, String userId,LocalDateTime transactionDate, BigDecimal amount, String keterangan, BigDecimal saldoBefore,  BigDecimal saldoAfter,
    //         LocalDateTime createdAt) {
    //     this.id = id;
    //     this.transactionCode = transactionCode;
    //     this.userId = userId;
    //     this.transactionDate = transactionDate;
    //     this.amount = amount;
    //     this.keterangan = keterangan;
    //     this.saldoBefore = saldoBefore;
    //     this.saldoAfter = saldoAfter;
    //     this.createdAt = createdAt;
    // }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

     public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getKeterangan() {
        return keterangan;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
        public BigDecimal getSaldoBefore() {
        return saldoBefore;
    }

    public void setSaldoBefore(BigDecimal saldoBefore) {
        this.saldoBefore = saldoBefore;
    }

    public BigDecimal getSaldoAfter() {
        return saldoAfter;
    }

    public void setSaldoAfter(BigDecimal saldoAfter) {
        this.saldoAfter = saldoAfter;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

   
    
}
