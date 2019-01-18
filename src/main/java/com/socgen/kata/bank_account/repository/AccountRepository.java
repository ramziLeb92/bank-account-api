package com.socgen.kata.bank_account.repository;

import com.socgen.kata.bank_account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

   Optional<Account> getAccountByAccountNumber(Integer accountNumber);

    @Query("Select a.amount FROM Account a WHERE a.accountNumber = :accountNumber")
    Double getAmountForAccountNumber(@Param("accountNumber") Integer accountNumber);
}
