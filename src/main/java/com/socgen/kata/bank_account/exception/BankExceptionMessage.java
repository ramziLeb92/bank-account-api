package com.socgen.kata.bank_account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BankExceptionMessage {

    ACCOUNT_NOT_FOUND("EXT001", "Account number %s has not been found."),
    INSUFFICIENT_FUND("EXT002","operation refused, insufficient fund for the account number %s.");

    public BankExceptionMessage formatMessage(String ... args) {
        message = String.format(message, (Object []) args);
        return this;
    }

    @Getter
    private String code;

    @Getter
    private String message;
}
