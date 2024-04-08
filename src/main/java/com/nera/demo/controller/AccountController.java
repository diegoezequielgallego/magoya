package com.nera.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nera.demo.dto.AccountDTO;
import com.nera.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<Long> createAccount(@RequestBody AccountDTO account) throws JsonProcessingException {
        Long accountId = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountId);
    }


    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable Long id) {
        double balance = accountService.getAccountBalance(id);
        return ResponseEntity.ok(balance);
    }


}
