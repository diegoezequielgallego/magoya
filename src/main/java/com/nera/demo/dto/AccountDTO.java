package com.nera.demo.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {

    private Long id;
    private String name;
    private String accountNumber;
    private double balance;
}
