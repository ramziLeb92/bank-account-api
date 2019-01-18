package com.socgen.kata.bank_account.service;

import com.socgen.kata.bank_account.dto.input.OperationRequestDto;
import com.socgen.kata.bank_account.model.Account;
import com.socgen.kata.bank_account.model.Client;
import com.socgen.kata.bank_account.model.Operation;
import com.socgen.kata.bank_account.repository.OperationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceTest {

    @Autowired
    private OperationService operationService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private OperationRepository operationRepository;

    private Client client = new Client(1,"Toto", "Titi","email");

    @Test
    public void should_register_operation() {
        Account account = new Account(1,123,50d, client);
        Operation operationResult = new Operation("deposit", 50d, account);
        OperationRequestDto operationRequestDto = OperationRequestDto.builder().accountNumber(123).operationAmount(50d).build();

        when(accountService.getAccount(anyInt())).thenReturn(Optional.of(account));
        doNothing()
                .when(accountService)
                .updateAccount(account);
        when(operationRepository.save(any()))
                .thenReturn(operationResult);

        Operation insertedOperation = operationService.registerOperation(operationRequestDto.getAccountNumber(),operationRequestDto.getOperationAmount());

        assertThat(insertedOperation, is(operationResult));
    }
}
