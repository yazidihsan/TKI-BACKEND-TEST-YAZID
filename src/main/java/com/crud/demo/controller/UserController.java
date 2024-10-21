package com.crud.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.dto.UserDto;
import com.crud.demo.dto.UserResponse;
import com.crud.demo.model.User; 
import com.crud.demo.service.UserService;
import com.crud.demo.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private JwtUtil jwtUtil; 

   private final UserService userService; 


 


   public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create user - restricted to ADMIN
    // @PreAuthorize("hasRole('ADMIN') or hasRole('FINANCE')")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto, @RequestHeader(value = "Authorization") String token) {
        if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
          }

         try{
             // Validate that the token is not null or empty
           if (token == null || token.isEmpty()) {
               return ResponseEntity.status(401).body("JWT token cannot be null or empty");
           }
           
              String role = jwtUtil.extractRole(token);



        System.out.println("Role auth: " + role);


        // Validate role from auth
        if(!role.equalsIgnoreCase("ROLE_ADMIN") && !role.equalsIgnoreCase("ROLE_FINANCE")){
          return ResponseEntity.status(400).body("Invalid role. Only ADMIN or FINANCE are allowed."); 
        }


     
           User createdUser = userService.createUser(userDto,token);
           return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(201, "User Created Successfully", createdUser));

         }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(500, e.getMessage(), null));
         }
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id,@RequestHeader(value = "Authorization") String token) {
         if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
          }

          try {
            if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }

            User user = userService.getUserById(id,token);
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(200, "Get User Successfully", user));
          } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(500, e.getMessage(),null ));
          }
       
        
    }

    // Update user - restricted to ADMIN
  //  @PreAuthorize("hasRole('ADMIN') or hasRole('FINANCE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UserDto userDto,@RequestHeader(value = "Authorization") String token) {
          if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
          }
          try {
            if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }
            User updatedUser = userService.updateUser(id, userDto,token);
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(200, "User Updated Successfully", updatedUser));
            
          } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(500, e.getMessage(), null));
          }
        
    }
 
    // List users with filtering, sorting, pagination
    @GetMapping
    public Page<?> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "nama") String sortField,
        @RequestParam(defaultValue = "ASC") String sortDirection,
        @RequestHeader(value = "Authorization") String token
    ) {
        if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
        }
        try {
           
            return userService.getUsers(page, size, sortField, sortDirection,token);
            
        } catch (Exception e) {
            return Page.empty(null);
        }
    }
    
}
