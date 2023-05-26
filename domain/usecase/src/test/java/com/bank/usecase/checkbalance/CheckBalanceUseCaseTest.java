package com.bank.usecase.checkbalance;

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
public class CheckBalanceUseCaseTest {

    @InjectMocks
    private CheckBalanceUseCase checkBalanceUseCase;

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
        Mono<Card> result = checkBalanceUseCase.checkBalance("1234567890");
        StepVerifier
                .create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}
