package com.crud.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String nama;
    private String phone;
    private String email;
    private String role;
    private String username;
    private String password;
    private BigDecimal saldo;
      // Initialize as an empty list
    private List<Withdrawal> withdrawalHistory = new ArrayList<>();
   
    private boolean status = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

       // Constructors
    public User() {}
    
      // Constructor
    public User(String id, String nama, String phone, String email, String role, String username, String password) {
        this.id = id;
        this.nama = nama;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.username = username;
        this.password = password;
    }
    

    // Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

     public List<Withdrawal> getWithdrawalHistory() {
        return withdrawalHistory;
    }

    public void addWithdrawalToHistory(Withdrawal withdrawal){
        if(this.withdrawalHistory == null){
            this.withdrawalHistory = new ArrayList<>();
        }
        this.withdrawalHistory.add(withdrawal);
    }

    public void setWithdrawalHistory(List<Withdrawal> withdrawalHistory) {
        this.withdrawalHistory = withdrawalHistory;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
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
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
   

    
}
