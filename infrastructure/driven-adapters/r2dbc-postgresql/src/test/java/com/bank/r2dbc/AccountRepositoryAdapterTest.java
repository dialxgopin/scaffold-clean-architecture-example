package com.bank.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import com.bank.model.account.Account;
import com.bank.r2dbc.Entities.Account.AccountEntity;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryAdapterTest {

    @InjectMocks
    AccountRepositoryAdapter repositoryAdapter;

    @Mock
    AccountRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void shouldSave() {
        when(repository.save(ArgumentMatchers.any())).thenReturn(Mono.just(new AccountEntity()));
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.eq(AccountEntity.class))).thenReturn(new AccountEntity());
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.eq(Account.class))).thenReturn(new Account());

        Mono<Account> result = repositoryAdapter.save(new Account());

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldFindById() {
        when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(new AccountEntity()));
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new Account());

        Mono<Account> result = repositoryAdapter.findById(1);

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}
