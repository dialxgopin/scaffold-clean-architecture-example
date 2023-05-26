package com.bank.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.bank.r2dbc.Entities.Card.CardEntity;

import reactor.core.publisher.Flux;

public interface CardRepository
        extends ReactiveCrudRepository<CardEntity, String>, ReactiveQueryByExampleExecutor<CardEntity> {
    Flux<CardEntity> findByCardId(String cardId);
}
