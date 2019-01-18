package com.socgen.kata.bank_account.repository;

import com.socgen.kata.bank_account.model.Account;
import com.socgen.kata.bank_account.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, String> {

    List<Operation> findOperationsByAccountAndDateBetweenOrderByDateDesc(Account account,
                                                                         LocalDate start,
                                                                         LocalDate end);
}
