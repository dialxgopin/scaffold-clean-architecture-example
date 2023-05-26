package com.bank.usecase.togglecard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ToggleCardUseCaseTest {

    @InjectMocks
    private ToggleCardUseCase toggleCardUseCase;

    @Mock
    private CardRepository cardRepository;

    @Test
    public void shouldActivateCard() {
        Card card = Card.builder()
                .cardId("1234567890")
                .active(false)
                .build();
        when(cardRepository.findByCardId(ArgumentMatchers.anyString())).thenReturn(Mono.just(card));
        when(cardRepository.save(ArgumentMatchers.any())).thenReturn(Mono.just(card));
        Mono<Card> result = toggleCardUseCase.activateCard("1234567890");
        StepVerifier
                .create(result)
                .consumeNextWith(updatedCard -> {
                    assertTrue(updatedCard.isActive());
                })
                .verifyComplete();
    }

    @Test
    public void shouldDeactivateCard() {
        Card card = Card.builder()
                .cardId("1234567890")
                .active(true)
                .build();
        when(cardRepository.findByCardId(ArgumentMatchers.anyString())).thenReturn(Mono.just(card));
        when(cardRepository.save(ArgumentMatchers.any())).thenReturn(Mono.just(card));
        Mono<Card> result = toggleCardUseCase.deactivateCard("1234567890");
        StepVerifier
                .create(result)
                .consumeNextWith(updatedCard -> {
                    assertFalse(updatedCard.isActive());
                })
                .verifyComplete();
    }
}
