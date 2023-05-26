package com.bank.r2dbc;

import com.bank.model.account.Account;
import com.bank.r2dbc.Entities.Account.AccountEntity;
import com.bank.r2dbc.helper.ReactiveAdapterOperations;

import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryAdapter extends
        ReactiveAdapterOperations<Account/* change for domain model */, AccountEntity/* change for adapter model */, Integer, AccountRepository>
        implements com.bank.model.account.gateways.AccountRepository {

    @Autowired
    public AccountRepositoryAdapter(AccountRepository repository, ObjectMapper mapper) {
        /**
         * Could be use mapper.mapBuilder if your domain model implement builder pattern
         * super(repository, mapper, d ->
         * mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         * Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Account.class/* change for domain model */));
    }

    @Override
    public Mono<Account> save(Account account) {
        return super.save(account);
    }

    @Override
    public Mono<Account> findById(Integer productId) {
        return repository.findById(productId).map(this::toEntity);
    }
}
