package com.bank.model.card.gateways;

import java.math.BigDecimal;

import com.bank.model.card.Card;

import reactor.core.publisher.Mono;

public interface CardRepository {
    Mono<Card> save(Card card);
    Mono<Card> findByCardId(String cardId);
    Mono<Card> deposit(String cardId, BigDecimal amount);
}
