package com.nera.demo.entity;

import com.nera.demo.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Builder
@Document(collection = "transaction_register")
public class TransactionMongo {

    @Id
    private String id;
    private Long accountId;
    private TransactionType transactionType;
    private double amount;
    private LocalDateTime requested;
}
