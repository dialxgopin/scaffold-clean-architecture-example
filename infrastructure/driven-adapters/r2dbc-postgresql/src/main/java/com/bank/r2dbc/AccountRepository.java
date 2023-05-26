package com.bank.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.bank.r2dbc.Entities.Account.AccountEntity;

public interface AccountRepository
        extends ReactiveCrudRepository<AccountEntity, Integer>, ReactiveQueryByExampleExecutor<AccountEntity> {
}
