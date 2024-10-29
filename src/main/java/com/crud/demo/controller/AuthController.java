package com.crud.demo.controller;
 

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.dto.AuthResponse;
import com.crud.demo.dto.LoginRequest;
import com.crud.demo.dto.RegisterRequest;
import com.crud.demo.dto.UpdatePasswordRequest;
import com.crud.demo.model.User;
import com.crud.demo.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader; 


@RestController
@RequestMapping("/api")
public class AuthController {
 
       private final AuthService authService; 
 


   public AuthController(AuthService authService) {
        this.authService = authService;
    }

     @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            // Validate input
            if(registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty()){
                return ResponseEntity.badRequest().body(new AuthResponse(400, "Username is required", null));
            }

            if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body(new AuthResponse(400, "Password is required", null));
            }

            if(registerRequest.getNama() == null || registerRequest.getNama().isEmpty()){
                return ResponseEntity.badRequest().body(new AuthResponse(400, "Name is required", null));
            }

            if(registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()){
                return ResponseEntity.badRequest().body(new AuthResponse(400, "Email is required", null));
            }

            if(registerRequest.getRole() == null || registerRequest.getRole().isEmpty()){
                return ResponseEntity.badRequest().body(new AuthResponse(400, "Role is required", null));
            }

            if(registerRequest.getPhone() == null || registerRequest.getPhone().isEmpty()){
                return ResponseEntity.badRequest().body(new AuthResponse(400, "Phone is required", null));
            }
            User registeredUser = authService.register(registerRequest.getUsername(),registerRequest.getPassword(),registerRequest.getNama(),registerRequest.getEmail(),registerRequest.getRole(),registerRequest.getPhone());

            return ResponseEntity.ok(new AuthResponse(201, "Berhasil Register", registeredUser)); // 201 Created
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(500, e.toString(), null)); // Handle registration errors
        }
    }

@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        try {
             // Validate input
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthResponse(400, "Username is required", null));
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new AuthResponse(400, "Password is required", null));
        }

        // Call the service to authenticate the user
        Optional<String> tokenOptional = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (tokenOptional.isPresent()) {
            String token = tokenOptional.get();
            return ResponseEntity.ok(new AuthResponse(200, "Berhasil Login", token));
        }

        return ResponseEntity.status(401).body(new AuthResponse(401, "Unauthorized", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(500, e.toString(), null));
        }
       
    }

   @GetMapping("/check-token")
      public ResponseEntity<?> checkToken(@RequestHeader(value = "Authorization") String token) {
          // Remove "Bearer " prefix if present
          if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
          }
           try { 
            Optional<User> userOptional = authService.checkToken(token);
          if (userOptional.isPresent()) {
              User user = userOptional.get();
              return ResponseEntity.ok(new AuthResponse(200, "Token available", user));
          }
          return ResponseEntity.status(401).body(new AuthResponse(401, "Token expired or invalid", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(500, e.toString(), null));
        }
    }

     @PutMapping("/{username}/update-password")
    public ResponseEntity<?> updatePassword(
            @PathVariable String username,
            @RequestBody UpdatePasswordRequest request) { 

         try { 
            User updatedUser = authService.updatePassword(username, request.getOldPassword(), request.getNewPassword(), request.getConfirmNewPassword());
            return ResponseEntity.ok(new AuthResponse(200, "Password Updated", updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponse(500, e.toString(), null));
        }


    }  

    @GetMapping("/logout")
        public ResponseEntity<?> logout(HttpServletRequest request) {
       
    
    try{    

         // Retrieve the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Ensure the Authorization header is present and follows the "Bearer <token>" format
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Authorization token is missing or invalid");
        }

        // Extract the token by removing the "Bearer " prefix
        String token = authorizationHeader.substring(7).trim(); // Trim in case of leading/trailing spaces

        // Validate that the token is not null or empty
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body("JWT token cannot be null or empty");
        }

    boolean logoutSuccess = authService.logout(token);

    if (logoutSuccess) {
        return ResponseEntity.ok(new AuthResponse(200, "Berhasil Logout",null));
    } else {
        return ResponseEntity.status(401).body(new AuthResponse(401, "Token expired",null));
    }
    }catch(RuntimeException e){
        return ResponseEntity.badRequest().body(new AuthResponse(500, e.toString(), null));
    }
}

 
   
}
