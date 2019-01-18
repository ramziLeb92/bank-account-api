package com.socgen.kata.bank_account.service;

import com.socgen.kata.bank_account.exception.AccountNotFoundException;
import com.socgen.kata.bank_account.exception.BankExceptionMessage;
import com.socgen.kata.bank_account.model.Account;
import com.socgen.kata.bank_account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    public Optional<Account> getAccount(int accountNumber) {
        return accountRepository.getAccountByAccountNumber(accountNumber);
    }

    public Double getAccountAmount(Integer accountNumber) throws AccountNotFoundException {
       return Optional.ofNullable(accountRepository.getAmountForAccountNumber(accountNumber))
                .orElseThrow(() ->
                        new AccountNotFoundException(BankExceptionMessage.ACCOUNT_NOT_FOUND.formatMessage(accountNumber.toString())));
    }
}
