package com.socgen.kata.bank_account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.socgen.kata.bank_account.util.LocalDateConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "operation_id")
    private Integer operationId;

    @Column
    private String label;

    @Column
    private Double amount;

    @Convert(converter = LocalDateConverter.class)
    @Column
    private LocalDate date;

    @Column(name = "balance")
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    public Operation(String operationType, Double operationValue, Account account) {
        this.label = operationType;
        this.amount = operationValue;
        this.account = account;
        this.balance = account.getAmount();
        this.date = LocalDate.now();
    }
}
