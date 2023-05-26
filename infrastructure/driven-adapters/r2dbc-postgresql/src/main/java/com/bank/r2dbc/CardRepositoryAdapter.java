package com.bank.r2dbc;

import com.bank.model.card.Card;
import com.bank.r2dbc.Entities.Card.CardEntity;
import com.bank.r2dbc.helper.ReactiveAdapterOperations;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CardRepositoryAdapter extends
        ReactiveAdapterOperations<Card/* change for domain model */, CardEntity/* change for adapter model */, String, CardRepository>
        implements com.bank.model.card.gateways.CardRepository {
    public CardRepositoryAdapter(CardRepository repository, ObjectMapper mapper) {
        /**
         * Could be use mapper.mapBuilder if your domain model implement builder pattern
         * super(repository, mapper, d ->
         * mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         * Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Card.class/* change for domain model */));
    }

    @Override
    public Mono<Card> save(Card card) {
        return super.save(card);
    }

    @Override
    public Mono<Card> findByCardId(String cardId) {
        return repository.findByCardId(cardId).map(this::toEntity).next();
    }

    @Override
    @Transactional
    public Mono<Card> deposit(String cardId, BigDecimal amount) {
        return this.findByCardId(cardId)
                .filter(card -> card.isActive())
                .doOnNext(card -> card.setBalance(card.getBalance().add(amount)))
                .flatMap(this::save);
    }
}
