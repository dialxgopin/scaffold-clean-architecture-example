package com.bank.usecase.spend;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
public class SpendUseCaseTest {
    @InjectMocks
    private SpendUseCase spendUseCase;

    @Mock
    private CardRepository cardRepository;

    @Test
    public void shouldDeposit() {
        Card card = Card.builder()
                .cardId("1234567890")
                .balance(BigDecimal.valueOf(1160000))
                .active(false)
                .build();
        when(cardRepository.deposit(ArgumentMatchers.anyString(), ArgumentMatchers.any(BigDecimal.class)))
                .thenReturn(Mono.just(card));
        Mono<Card> result = spendUseCase.spend("1234567890", BigDecimal.valueOf(150950));
        StepVerifier
                .create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}
