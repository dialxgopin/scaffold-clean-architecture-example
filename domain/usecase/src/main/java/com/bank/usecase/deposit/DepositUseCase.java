package com.bank.usecase.deposit;

import java.math.BigDecimal;

import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DepositUseCase {

    @NonNull
    private CardRepository cardRepository;

    public Mono<Card> deposit(String cardId, BigDecimal amount) {
        return cardRepository.deposit(cardId, amount);
    }
}
