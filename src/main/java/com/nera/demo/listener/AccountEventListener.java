package com.nera.demo.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nera.demo.dto.AccountDTO;
import com.nera.demo.entity.AccountMongo;
import com.nera.demo.repository.AccountMongoRepository;
import com.nera.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountEventListener {

    private final ObjectMapper objectMapper;

    @Autowired
    private AccountMongoRepository accountRepository;

    public AccountEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "account-events", groupId = "group_id")
    public void listen(String accountJson) {

        try {
            AccountDTO accountDTO = objectMapper.readValue(accountJson, AccountDTO.class);
            System.out.println("Received AccountDTO: " + accountDTO);

            AccountMongo accountMongo = AccountMongo.builder()
                    .accountNumber(accountDTO.getAccountNumber())
                    .name(accountDTO.getName())
                    .requested(LocalDateTime.now())
                    .build();
            accountRepository.save(accountMongo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
