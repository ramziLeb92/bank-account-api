package com.socgen.kata.bank_account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends GenericBankException {

    public AccountNotFoundException(BankExceptionMessage bankExceptionMessage) {
        super(bankExceptionMessage);
    }
}
