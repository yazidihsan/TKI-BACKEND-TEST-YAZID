package com.crud.demo.service;

import java.util.Arrays;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.crud.demo.model.User;
import com.crud.demo.dto.UserDto;
import com.crud.demo.repository.UserRepository; 

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;


   

    //  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(UserDto userDto,String token){
            // Log the token for audit purposes
        System.out.println("Authorization Token: " + token);

     
        
      
        
         validateUserDto(userDto,token);


    

         User user = new User();


        user.setNama(userDto.getNama());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setUsername(userDto.getUsername());

        // Generate password (short form of name + last 4 digits of phone)
        String password = generatePassword(user.getNama(), user.getPhone());
        user.setPassword(password);

        return userRepository.save(user);
    }

    // Generate password from name and phone
    private String generatePassword(String nama, String phone) {
        String[] words = nama.split(" ");
        String shortName = Arrays.stream(words).map(word -> word.substring(0, 1)).collect(Collectors.joining());
        return shortName.toLowerCase() + phone.substring(phone.length() - 4);
    }


     // Validate input fields and check for uniqueness
    private void validateUserDto(UserDto userDto,String token) {


        // Check if email is already in use
        Optional<User> existingUserByEmail = userRepository.findByEmail(userDto.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Check if username is already in use
        Optional<User> existingUserByUsername = userRepository.findByUsername(userDto.getUsername());
        if (existingUserByUsername.isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        } 
        // Optional<User> existingUserByRoleAdmin = userRepository.findByRole("ROLE_ADMIN");
        // Optional<User> existingUserByRoleFinance = userRepository.findByRole("ROLE_FINANCE");

        
        // User userAdmin = existingUserByRoleAdmin.get();
        // User userFinance = existingUserByRoleFinance.get();
        // System.out.println("user role"+userAdmin.getRole());
        // System.out.println("user role"+userFinance.getRole());
        
        //  User user =  existingUserByUsername.get();

        //  System.out.println("Nama ROle adalah" +user.getRole());

        //  if(!user.getRole().equalsIgnoreCase("ROLE_ADMIN") || !user.getRole().equalsIgnoreCase("ROLE_FINANCE")){
        //     throw new IllegalArgumentException("Invalid role. Only ADMIN or FINANCE are allowed.");
        //  }

     
        // Validate role from userDTO
        if (!userDto.getRole().equalsIgnoreCase("ROLE_ADMIN") && !userDto.getRole().equalsIgnoreCase("ROLE_FINANCE")) {
            throw new IllegalArgumentException("Invalid role. Only ADMIN or FINANCE are allowed.");
        }

       
        // Validate phone
        if (!userDto.getPhone().matches("\\d+")) {
            throw new IllegalArgumentException("Phone must contain only numbers.");
        }

        if (userDto.getPhone().length() < 8 || userDto.getPhone().length() > 15) {
            throw new IllegalArgumentException("Phone must be between 8 and 15 characters long.");
        }
    }

      // Get user by ID
    public User getUserById(String id, String token) {
                  // Log the token for audit purposes
        System.out.println("Authorization Token: " + token);
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Update user restricted to ADMIN role
    public User updateUser(String id, UserDto userDto,String token) {
                  // Log the token for audit purposes
        System.out.println("Authorization Token: " + token);
        User user = getUserById(id,token);
        validateUserDto(userDto,token);

        user.setNama(userDto.getNama());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setUsername(userDto.getUsername());

        return userRepository.save(user);
    }

       // Filtering, sorting, pagination
    public Page<User> getUsers(int page, int size, String sortField, String sortDirection, String token) {

     // Log the token for audit purposes
        System.out.println("Authorization Token: " + token);


        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }
  
  

}
