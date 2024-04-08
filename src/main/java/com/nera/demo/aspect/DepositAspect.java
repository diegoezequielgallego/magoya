package com.nera.demo.aspect;

import com.nera.demo.dto.TransactionDTO;
import com.nera.demo.enums.TransactionType;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DepositAspect {

    @Before("execution(* com.nera.demo.service.AccountService.performTransaction(..)) && args(transactionDTO)")
    public void logLargeDeposits(TransactionDTO transactionDTO) {
        if (transactionDTO.getAmount() > 10000) {
            System.out.println("Transaction of more than $10,000 detected!!!!!!!!!!!");
        }
    }
}
