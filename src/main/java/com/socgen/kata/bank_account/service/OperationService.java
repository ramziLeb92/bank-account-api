package com.socgen.kata.bank_account.service;

import com.socgen.kata.bank_account.exception.AccountNotFoundException;
import com.socgen.kata.bank_account.exception.BankExceptionMessage;
import com.socgen.kata.bank_account.exception.InsufficientFundException;
import com.socgen.kata.bank_account.model.Account;
import com.socgen.kata.bank_account.model.Operation;
import com.socgen.kata.bank_account.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.DoubleFunction;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.notNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;

    private final AccountService accountService;

    private static final DoubleFunction<String> evaluateOperationType = operation -> operation > 0 ? "deposit" : "withdrawl";

    @Transactional
    public Operation registerOperation(Integer accountNumber, Double operationAmount) {

        Account account = accountService.getAccount(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(
                        BankExceptionMessage.ACCOUNT_NOT_FOUND.formatMessage(accountNumber.toString())
                ));

        Double newAccountStatus = account.getAmount() + operationAmount;

        account.setAmount(newAccountStatus);
        log.info("Account {} amount updated", account.getAccountNumber());
        if (newAccountStatus < 0) {
            throw new InsufficientFundException(BankExceptionMessage.INSUFFICIENT_FUND.formatMessage(accountNumber.toString()));
        }
        accountService.updateAccount(account);
        Operation operation = new Operation(evaluateOperationType.apply(operationAmount), operationAmount, account);
        return operationRepository.save(operation);
    }

    public List<Operation> findOperations(Integer accountNumber,
                                          LocalDate startOperationDate,
                                          LocalDate endOperationDate) {
        Account account = accountService.getAccount(notNull(accountNumber))
                .orElseThrow(() -> new AccountNotFoundException(
                        BankExceptionMessage.ACCOUNT_NOT_FOUND.formatMessage(accountNumber.toString())
                ));
        return operationRepository
                .findOperationsByAccountAndDateBetweenOrderByDateDesc(account,
                        requireNonNull(startOperationDate),
                        requireNonNull(endOperationDate));
    }


}
