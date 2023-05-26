package com.bank.usecase.spend;

import java.math.BigDecimal;

import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class SpendUseCase {

    @NonNull
    private CardRepository cardRepository;

    public Mono<Card> spend(String cardId, BigDecimal amount) {
        return cardRepository.deposit(cardId, amount.negate());
    }
}
