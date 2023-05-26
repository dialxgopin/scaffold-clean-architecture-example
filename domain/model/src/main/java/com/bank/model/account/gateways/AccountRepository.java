package com.bank.model.account.gateways;

import com.bank.model.account.Account;

import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<Account> save(Account account);
    Mono<Account> findById(Integer productId);
}
