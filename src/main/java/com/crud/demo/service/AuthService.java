package com.crud.demo.service;

import com.crud.demo.model.User;
import com.crud.demo.repository.UserRepository;
import com.crud.demo.util.JwtUtil;
 

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil; 

    private Set<String> blacklistedTokens = new HashSet<>();

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(String username, String password, String nama, String email, String role, String phone) {
        User user = new User();
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        // User user = existingUser.get();
        if(existingUser.isPresent()){
            throw new RuntimeException("Username already exists");
        }
        user.setUsername(username);

        existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new RuntimeException("Email already exists");
        }
        user.setEmail(email);

        user.setNama(nama);

        if("ADMIN".equals(role)){
            user.setRole("ROLE_ADMIN"); 
        }else if("FINANCE".equals(role)){
            user.setRole("ROLE_FINANCE"); 
        }


        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("system"); 


        return userRepository.save(user);
    }

      public Optional<String> login(String username, String password) {
        // Find the user by username
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Generate JWT token
                String token = jwtUtil.generateToken(username, user.getRole());
                return Optional.of(token); // Return the generated token
            }
        }
        return Optional.empty(); // Return empty if credentials are invalid
    }

      public Optional<User> checkToken(String token) {
        // Check if the token is valid
        if (jwtUtil.isTokenExpired(token)) {
            return Optional.empty(); // Token is expired
        }

        // Extract username from the token
        String username = jwtUtil.extractUsername(token);
        // Fetch the user data from the repository
        return userRepository.findByUsername(username);
    }

    
    public User updatePassword(String username, String oldPassword, String newPassword,String confirmNewPassword)  {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate the old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Validate new password
        if (!isValidNewPassword(newPassword)) {
            throw new RuntimeException("New password does not meet complexity requirements.");
        }

         // Validate confirm new password
        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("Confirm new password does not match new password.");
        }


        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now()); // Update timestamp
        user.setUpdatedBy(username); // Update user who made the change

        return userRepository.save(user); // Save changes
    }

     private boolean isValidNewPassword(String newPassword) {
         // Check for minimum length (6 characters)
        if (newPassword.length() < 6) return false;

        // Check for at least one uppercase, one lowercase, and one special character
        return newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");

    }


    public boolean logout(String token) {
        String username = jwtUtil.extractUsername(token);
        if (username == null || !jwtUtil.validateToken(token, username)) {
            return false;
        }

        // Add the token to the blacklist
        blacklistedTokens.add(token);

        return true;
    }
 
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

}
