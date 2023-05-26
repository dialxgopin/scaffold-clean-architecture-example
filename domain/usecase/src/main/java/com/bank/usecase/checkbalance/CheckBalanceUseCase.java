package com.bank.usecase.checkbalance;

import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class CheckBalanceUseCase {

    @NonNull
    private CardRepository cardRepository;

    public Mono<Card> checkBalance(String cardId) {
        return cardRepository.findByCardId(cardId);
    }
}
