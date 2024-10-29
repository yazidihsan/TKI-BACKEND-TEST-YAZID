package com.crud.demo.service;

 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.crud.demo.dto.TopUpRequest; 
import com.crud.demo.dto.WithdrawalRequest; 
import com.crud.demo.model.Transaction;
import com.crud.demo.model.User;
import com.crud.demo.model.Withdrawal;
import com.crud.demo.repository.TransactionRepository;
import com.crud.demo.repository.UserRepository;
import com.crud.demo.repository.WithdrawalRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private UserRepository userRepository;

    public Transaction processTopUp(TopUpRequest request, String token){

        // Log the token for audit purposes
        System.out.println("Authorization Token: " + token);

        Optional<User> user = userRepository.findById(request.getUserId());

        if(!user.isPresent()){
            throw new RuntimeException("User not found");
        }

        if(request.getAmount().compareTo(BigDecimal.valueOf(5000)) < 0){
            throw new RuntimeException("Minimum amount is 5000");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUserId(request.getUserId());
        transaction.setKeterangan(request.getKeterangan());
        transaction.setAmount(request.getAmount());
        transaction.setAdmin(request.getAmount().multiply(BigDecimal.valueOf(0.10)));
        transaction.setTotal(request.getAmount().subtract(transaction.getAdmin()));
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCreatedBy(user.get().getUsername());

        //Generate kode
        String kode = generateTransactionCode();
        transaction.setKode(kode);

        BigDecimal currentSaldo = user.get().getSaldo();
        if(currentSaldo == null){
            currentSaldo = BigDecimal.ZERO;
        }
        //Update user saldo
        user.get().setSaldo(currentSaldo.add(transaction.getTotal()));
        
        userRepository.save(user.get());
        
        
        return transactionRepository.save(transaction);
       

    }

    public Withdrawal processWithdrawal(WithdrawalRequest request, String token){


        // Log the token for audit purposes
        System.out.println("Authorization Token: " + token);

        Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if(!optionalUser.isPresent()){
            throw new RuntimeException("User not found");
        }
         User user = optionalUser.get();
          // Validate that the withdrawal amount does not exceed the user's saldo
        BigDecimal withdrawalAmount = request.getAmount();
        if(withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0 || withdrawalAmount.compareTo(user.getSaldo()) > 0){
            throw new RuntimeException("Invalid withdrawal amount");
        }

         // Generate transaction code for withdrawal
         String transactionCode = generateTransactionCode();

         // Record saldo before and after withdrawal
         BigDecimal saldoBefore = user.getSaldo();
         BigDecimal saldoAfter = saldoBefore.subtract(withdrawalAmount);

         // Update User's saldo
         user.setSaldo(saldoAfter);
        

         // Create withdrawal history entry
         Withdrawal withdrawal = new Withdrawal();
         withdrawal.setUserId(request.getUserId());
         withdrawal.setAmount(withdrawalAmount);
         withdrawal.setKeterangan(request.getKeterangan());
         withdrawal.setTransactionCode(transactionCode);
         withdrawal.setTransactionDate(LocalDateTime.now());
         withdrawal.setSaldoBefore(saldoBefore);
         withdrawal.setSaldoAfter(saldoAfter);
         withdrawal.setCreatedBy(user.getUsername());

         // Add withdrawal entry to the user's history
         user.addWithdrawalToHistory(withdrawal);
         userRepository.save(user);

         //Save withdrawal entry 

        return  withdrawalRepository.save(withdrawal);


    }

    private String generateTransactionCode() {
       
        //Get the current year and month in YYMM format
        LocalDateTime now = LocalDateTime.now();
        String currentYYMM = now.format(DateTimeFormatter.ofPattern("yyMM"));
        
        // Find the last transaction code for the current month
        Optional<Transaction> lastTransaction = transactionRepository.findFirstByKodeStartingWithOrderByKodeDesc(currentYYMM);

        int newIncrement = 1; // Default increment;

        if(lastTransaction.isPresent()){
            // Extract the last 4 digits from the last transaction's kode
            String lastKode = lastTransaction.get().getKode();
            String lastIncrementStr = lastKode.substring(lastKode.length() - 4);

            // Convert to an integer and increment
            newIncrement = Integer.parseInt(lastIncrementStr) + 1;
        }

        // Format the new increment with 4 digits (e.g., 0001, 0002)
        String newIncrementStr = String.format("%04d", newIncrement);

        // Return the full transaction code in YYMM-INCREMENT4DIGIT
        return currentYYMM + "-" + newIncrementStr;



    }
}
