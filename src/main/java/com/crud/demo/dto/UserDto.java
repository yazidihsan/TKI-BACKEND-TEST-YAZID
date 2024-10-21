package com.crud.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotEmpty(message = "Nama is required")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Nama cannot contain special characters")
    @Size(min = 3, message = "Nama must be at least 3 characters long")
    private String nama;

    @NotEmpty(message = "Phone is required")
    @Size(min = 8, max = 15, message = "Phone must be between 8 and 15 characters long")
    @Pattern(regexp = "^[0-9]+$", message = "Phone must be numeric")
    private String phone;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Role is required")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_FINANCE", message = "Role can only be ADMIN or FINANCE")
    private String role;

    @NotEmpty(message = "Username is required")
    private String username;

   

    public UserDto(){
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
}
