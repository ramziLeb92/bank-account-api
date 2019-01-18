package com.socgen.kata.bank_account.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationsHistoryDto {

    private String label;

    private Double amount;

    private LocalDate date;

    private Double balance;

}
