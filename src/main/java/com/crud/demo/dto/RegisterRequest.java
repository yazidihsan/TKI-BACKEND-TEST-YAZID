package com.crud.demo.dto;

import java.time.LocalDateTime;

public class RegisterRequest {
    
    private String username;
    private String password;
    private String nama;
    private String email;
    private String role;
    private String phone;
    // private LocalDateTime createdAt = LocalDateTime.now();
    // private String createdBy;

    // public LocalDateTime getCreatedAt() {
    //     return createdAt;
    // }
    // public void setCreatedAt(LocalDateTime createdAt) {
    //     this.createdAt = createdAt;
    // }
    // public String getCreatedBy() {
    //     return createdBy;
    // }
    // public void setCreatedBy(String createdBy) {
    //     this.createdBy = createdBy;
    // }
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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
