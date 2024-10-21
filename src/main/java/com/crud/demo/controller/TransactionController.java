package com.crud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.demo.dto.TopUpRequest;
import com.crud.demo.dto.TransactionResponse;
import com.crud.demo.dto.WithdrawalRequest;
import com.crud.demo.dto.WithdrawalResponse;
import com.crud.demo.model.Transaction;
import com.crud.demo.model.Withdrawal;
import com.crud.demo.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/topup")
    public ResponseEntity<?> topUp(@RequestBody TopUpRequest request,@RequestHeader(value = "Authorization") String token) {
         if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
          }
        try {

            if (token == null || token.isEmpty()) {
                return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }

            Transaction response = transactionService.processTopUp(request,token);
 
            return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(201, "Transaction Created Successfully", response));
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TransactionResponse(500, e.getMessage(), null));
        }
         
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawalRequest request, @RequestHeader(value = "Authorization") String token) {
       
           if (token.startsWith("Bearer ")) {
              token = token.substring(7); // Remove the "Bearer " part
          }
          try {
            if(token == null || token.isEmpty()){
                return ResponseEntity.status(401).body("JWT token cannot be null or empty");
            }

            Withdrawal response  = transactionService.processWithdrawal(request, token);


            return ResponseEntity.status(HttpStatus.OK).body(new WithdrawalResponse(200, "Withdrawal successful", response));
            
          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TransactionResponse(500, e.getMessage(), null));
          }
        
    }
    


    
}
