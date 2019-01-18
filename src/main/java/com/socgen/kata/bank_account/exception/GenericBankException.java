package com.socgen.kata.bank_account.exception;

public class GenericBankException extends RuntimeException {

    BankExceptionMessage bankExceptionMessage;

    public GenericBankException(BankExceptionMessage bankExceptionMessage) {
        super(bankExceptionMessage.getMessage());
        this.bankExceptionMessage = bankExceptionMessage;
    }
}
