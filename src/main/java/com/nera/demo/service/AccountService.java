package com.nera.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nera.demo.dto.AccountDTO;
import com.nera.demo.dto.TransactionDTO;
import com.nera.demo.entity.Account;
import com.nera.demo.enums.TransactionType;
import com.nera.demo.exception.InsufficientBalanceException;
import com.nera.demo.exception.InvalidAccountException;
import com.nera.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Creates a new bank account and saves it in the database.
     *
     * @param accountDTO The account details to create.
     * @return The ID of the created account.
     * @throws JsonProcessingException If there is an error converting the object to JSON.
     * @throws InvalidAccountException If the account is invalid.
     */
    public Long createAccount(AccountDTO accountDTO) throws JsonProcessingException {
        Account account = Account.builder()
                .name(accountDTO.getName())
                .accountNumber(accountDTO.getAccountNumber())
                .balance(0)
                .build();

        if (account.isValid()) {
            Account savedAccount = accountRepository.save(account);
            ObjectMapper objectMapper = new ObjectMapper();
            String accountJson = objectMapper.writeValueAsString(accountDTO);
            kafkaTemplate.send("account-events", accountJson);
            return savedAccount.getId();
        } else {
            throw new InvalidAccountException("invalid account");
        }

    }

    /**
     * Gets the balance of a bank account given its ID.
     *
     * @param id The ID of the account.
     * @return The balance of the account.
     * @throws InvalidAccountException If the account with the specified ID is not found.
     */
    public double getAccountBalance(Long id) {
        Double balance = accountRepository.findBalanceById(id);
        if (balance != null) {
            return balance;
        } else {
            throw new InvalidAccountException("Account not found with id: " + id);
        }

    }

    /**
     * Performs a bank transaction, either deposit or withdrawal, and saves the transaction details in the database.
     *
     * @param transactionDTO The transaction details.
     * @throws Exception If there is an error performing the transaction.
     */
    public void performTransaction(TransactionDTO transactionDTO) throws Exception {

        Long accountId = transactionDTO.getAccountId();
        TransactionType transactionType = transactionDTO.getTransactionType();
        double amount = transactionDTO.getAmount();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new InvalidAccountException("Account not found with id: " + accountId));

        switch (transactionType) {
            case DEPOSIT:
                account.setBalance(account.getBalance() + amount);
                break;
            case WITHDRAWAL:
                if (amount > account.getBalance()) {
                    throw new InsufficientBalanceException("Insufficient balance");
                }
                account.setBalance(account.getBalance() - amount);
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }

        accountRepository.save(account);

        ObjectMapper objectMapper = new ObjectMapper();
        String transactionJson = objectMapper.writeValueAsString(transactionDTO);
        kafkaTemplate.send("transaction-events", transactionJson);
    }
}
