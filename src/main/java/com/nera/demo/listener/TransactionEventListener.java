package com.nera.demo.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nera.demo.dto.AccountDTO;
import com.nera.demo.dto.TransactionDTO;
import com.nera.demo.entity.AccountMongo;
import com.nera.demo.entity.TransactionMongo;
import com.nera.demo.repository.AccountMongoRepository;
import com.nera.demo.repository.TransactionMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionEventListener {

    private final ObjectMapper objectMapper;

    @Autowired
    private TransactionMongoRepository transactionMongoRepository;

    public TransactionEventListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "transaction-events", groupId = "group_id")
    public void listen(String transactionJson) {

        try {
            TransactionDTO transactionDTO = objectMapper.readValue(transactionJson, TransactionDTO.class);
            System.out.println("Received transactionDTO: " + transactionDTO);

            TransactionMongo transaction = TransactionMongo.builder()
                    .accountId(transactionDTO.getAccountId())
                    .transactionType(transactionDTO.getTransactionType())
                    .amount(transactionDTO.getAmount())
                    .requested(LocalDateTime.now())
                    .build();
            transactionMongoRepository.save(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
