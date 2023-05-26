package com.bank.usecase.accountcreation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.model.account.Account;
import com.bank.model.account.gateways.AccountRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class AccountCreationUseCaseTest {

    @InjectMocks
    private AccountCreationUseCase accountCreationUseCase;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void shouldCreateAccount() {
        Account account = Account.builder()
                .accountHolderFisrtname("Name")
                .accountHolderLastname("Last Name")
                .build();
        when(accountRepository.save(ArgumentMatchers.any())).thenReturn(Mono.just(account));
        Mono<Account> result = accountCreationUseCase.createAccount(account.getAccountHolderFisrtname(),
                account.getAccountHolderLastname());
        StepVerifier
                .create(result)
                .consumeNextWith(newAccount -> {
                    assertEquals(
                            account.getAccountHolderFisrtname()
                                    + account.getAccountHolderLastname(),
                            newAccount.getAccountHolderFisrtname()
                                    + newAccount.getAccountHolderLastname());
                })
                .verifyComplete();
    }

}
