package com.socgen.kata.bank_account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientFundException extends GenericBankException {

    public InsufficientFundException(BankExceptionMessage bankExceptionMessage) {
        super(bankExceptionMessage);
    }
}