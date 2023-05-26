package com.bank.api;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.bank.api.dto.AccountHolder;
import com.bank.api.dto.Balance;
import com.bank.api.dto.Card;
import com.bank.api.validator.Validations;
import com.bank.usecase.accountcreation.AccountCreationUseCase;
import com.bank.usecase.checkbalance.CheckBalanceUseCase;
import com.bank.usecase.deposit.DepositUseCase;
import com.bank.usecase.issuecard.IssueCardUseCase;
import com.bank.usecase.spend.SpendUseCase;
import com.bank.usecase.togglecard.ToggleCardUseCase;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    @Autowired
    private final IssueCardUseCase issueCardUseCase;

    @Autowired
    private final AccountCreationUseCase accountCreationUseCase;

    @Autowired
    private final ToggleCardUseCase toggleCardUseCase;

    @Autowired
    private final DepositUseCase depositUseCase;

    @Autowired
    private final CheckBalanceUseCase checkBalanceUseCase;

    @Autowired
    private final SpendUseCase spendUseCase;

    public Mono<ServerResponse> generateCardNumber(ServerRequest serverRequest) {
        return ServerResponse.ok().body(
                issueCardUseCase.issueCard(
                        Integer.valueOf(serverRequest.pathVariable("productId")))
                        .map(card -> Card.fromCardModel(card)),
                Card.class);
    }

    public Mono<ServerResponse> createAccount(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(AccountHolder.class)
                .doOnNext(Validations::validate)
                .flatMap(holder -> {
                    return accountCreationUseCase.createAccount(holder.getAccountHolderFisrtname(),
                            holder.getAccountHolderLastname());
                })
                .flatMap(data -> ServerResponse.ok().bodyValue(data))
                .onErrorResume(e -> {
                    return Mono.error(e);
                });
    }

    public Mono<ServerResponse> enrollCard(ServerRequest serverRequest) {
        return ServerResponse.ok().body(
                toggleCardUseCase.activateCard(serverRequest.pathVariable("cardId"))
                        .map(card -> Card.fromCardModel(card)),
                Card.class);
    }

    public Mono<ServerResponse> lockCard(ServerRequest serverRequest) {
        return ServerResponse.ok().body(
                toggleCardUseCase.deactivateCard(serverRequest.pathVariable("cardId"))
                        .map(card -> Card.fromCardModel(card)),
                Card.class);
    }

    public Mono<ServerResponse> deposit(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Balance.class)
                .doOnNext(Validations::validate)
                .flatMap(deposit -> {
                    return depositUseCase.deposit(deposit.getCardId(), deposit.getBalance());
                })
                .map(card -> {
                    return new Balance(card.getCardId(), card.getBalance());
                })
                .flatMap(data -> ServerResponse.ok().bodyValue(data))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> checkBalance(ServerRequest serverRequest) {
        return checkBalanceUseCase.checkBalance(serverRequest.pathVariable("cardId"))
                .map(card -> {
                    return new Balance(card.getCardId(), card.getBalance());
                })
                .flatMap(data -> ServerResponse.ok().bodyValue(data))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> spend(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Balance.class)
                .doOnNext(Validations::validate)
                .flatMap(spend -> {
                    return spendUseCase.spend(spend.getCardId(), spend.getBalance());
                })
                .map(card -> {
                    return new Balance(card.getCardId(), card.getBalance());
                })
                .flatMap(data -> ServerResponse.ok().bodyValue(data))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
