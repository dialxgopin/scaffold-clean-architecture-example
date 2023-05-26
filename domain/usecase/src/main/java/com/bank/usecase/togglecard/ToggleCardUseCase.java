package com.bank.usecase.togglecard;

import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ToggleCardUseCase {

    @NonNull
    private CardRepository cardRepository;

    static final boolean enable = true;

    static final boolean disable = false;

    public Mono<Card> activateCard(String cardId) {
        return cardRepository.findByCardId(cardId)
                .flatMap(card -> {
                    card.setActive(enable);
                    return cardRepository.save(card);
                });
    }

    public Mono<Card> deactivateCard(String cardId) {
        return cardRepository.findByCardId(cardId)
                .flatMap(card -> {
                    card.setActive(disable);
                    return cardRepository.save(card);
                });
    }
}
