package com.socgen.kata.bank_account.dto.input;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequestDto {

    @NotNull
    private Integer accountNumber;
    @NotNull
    private Double operationAmount;
}
