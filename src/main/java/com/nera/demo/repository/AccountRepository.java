package com.nera.demo.repository;

import com.nera.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a.balance FROM Account a WHERE a.id = :id")
    Double findBalanceById(Long id);

}
