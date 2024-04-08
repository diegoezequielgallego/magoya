package com.nera.demo.dto;

import com.nera.demo.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {

    private Long accountId;
    private TransactionType transactionType;
    private double amount;

}
