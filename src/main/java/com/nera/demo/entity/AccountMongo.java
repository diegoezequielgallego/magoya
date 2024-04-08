package com.nera.demo.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "account_register")
public class AccountMongo {

    @Id
    private String id;
    private String name;
    private String accountNumber;
    private LocalDateTime requested;
    private double balance;



}
