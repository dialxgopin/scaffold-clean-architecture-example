package com.bank.r2dbc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import com.bank.model.card.Card;
import com.bank.r2dbc.Entities.Card.CardEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CardRepositoryAdapterTest {

    @InjectMocks
    CardRepositoryAdapter repositoryAdapter;

    @Mock
    CardRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void shouldSave() {
        when(repository.save(ArgumentMatchers.any())).thenReturn(Mono.just(new CardEntity()));
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.eq(CardEntity.class))).thenReturn(new CardEntity());
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.eq(Card.class))).thenReturn(new Card());

        Mono<Card> result = repositoryAdapter.save(new Card());

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldFindById() {
        when(repository.findByCardId(ArgumentMatchers.anyString())).thenReturn(Flux.just(new CardEntity()));
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new Card());

        Mono<Card> result = repositoryAdapter.findByCardId("1234567890123456");

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldDeposit() {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setBalance(BigDecimal.ZERO);
        cardEntity.setActive(true);
        Card card = Card.builder()
                .balance(BigDecimal.ZERO)
                .active(true)
                .build();
        when(repository.findByCardId(ArgumentMatchers.anyString())).thenReturn(Flux.just(new CardEntity()));
        when(repository.save(ArgumentMatchers.any())).thenReturn(Mono.just(new CardEntity()));
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.eq(CardEntity.class))).thenReturn(cardEntity);
        when(mapper.map(ArgumentMatchers.any(), ArgumentMatchers.eq(Card.class))).thenReturn(card);

        Mono<Card> result = repositoryAdapter.deposit("1234567890123456", BigDecimal.valueOf(300000.0));

        StepVerifier.create(result)
                .consumeNextWith(updatedCard -> {
                    assertTrue(BigDecimal.valueOf(300000.0).compareTo(updatedCard.getBalance()) == 0);
                })
                .verifyComplete();
    }
}
