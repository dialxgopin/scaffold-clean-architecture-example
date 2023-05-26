package com.bank.usecase.accountcreation;

import com.bank.model.account.Account;
import com.bank.model.account.gateways.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AccountCreationUseCase {

    @NonNull
    private AccountRepository accountRepository;

    public Mono<Account> createAccount(String firstName, String lastName) {
        return accountRepository.save(Account.builder()
                .accountHolderFisrtname(firstName)
                .accountHolderLastname(lastName)
                .build());
    }
}
