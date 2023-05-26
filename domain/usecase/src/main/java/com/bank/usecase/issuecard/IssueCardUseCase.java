package com.bank.usecase.issuecard;

import java.math.BigDecimal;
import com.bank.model.account.gateways.AccountRepository;
import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;
import com.bank.usecase.helper.Utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class IssueCardUseCase {

    @NonNull
    private CardRepository cardRepository;
    @NonNull
    private AccountRepository accountRepository;

    static final short randomDigits = 10;
    static final boolean activeCard = false;
    static final int expirationYears = 3;
    static final String currency = "USD";
    static final short paddingLimit = 16;

    public Mono<Card> issueCard(Integer productId) {
        String cardNumber = productId + Utils.randomNumeric(randomDigits);
        if (cardNumber.length() < paddingLimit) {
            cardNumber = Utils.zeros(paddingLimit - cardNumber.length()) + cardNumber;
        }
        return cardRepository.save(
                Card.builder()
                        .cardId(cardNumber)
                        .accountId(productId)
                        .validThru(Utils.expirationDate(expirationYears))
                        .currency(currency)
                        .balance(BigDecimal.ZERO)
                        .active(activeCard)
                        .build());
    }
}
