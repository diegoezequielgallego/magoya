package com.nera.demo.controller;

import com.nera.demo.dto.TransactionDTO;
import com.nera.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/transactions")
    public ResponseEntity<String> performTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        accountService.performTransaction(transactionDTO);
        return ResponseEntity.ok("Transaction completed successfully");
    }
}
