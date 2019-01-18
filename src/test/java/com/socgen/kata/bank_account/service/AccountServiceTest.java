package com.socgen.kata.bank_account.service;

import com.socgen.kata.bank_account.exception.AccountNotFoundException;
import com.socgen.kata.bank_account.repository.AccountRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    private static Integer ACCOUNT_NUMBER = 23;

    @Before
    public void setUp() {
        when(accountRepository.getAmountForAccountNumber(ACCOUNT_NUMBER))
                .thenReturn(50d);
    }

    @Test
    public void should_get_account_amount_for_account_number() {
        assertThat(accountService.getAccountAmount(ACCOUNT_NUMBER), is(50d));
    }

    @Test(expected = AccountNotFoundException.class)
    public void should_throw_account_not_found_when_trying_to_get_account_amount_for_unknown_account_number() throws AccountNotFoundException {
        when(accountRepository.getAmountForAccountNumber(ACCOUNT_NUMBER))
                .thenReturn(null);
        accountService.getAccountAmount(ACCOUNT_NUMBER);
    }
}
