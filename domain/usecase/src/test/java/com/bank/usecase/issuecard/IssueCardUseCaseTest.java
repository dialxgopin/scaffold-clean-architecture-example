package com.bank.usecase.issuecard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.model.account.Account;
import com.bank.model.account.gateways.AccountRepository;
import com.bank.model.card.Card;
import com.bank.model.card.gateways.CardRepository;
import com.bank.usecase.helper.Utils;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class IssueCardUseCaseTest {
    @InjectMocks
    private IssueCardUseCase issueCardUseCase;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardRepository cardRepository;

    private Card card;

    private Account account;

    @BeforeEach 
    void init() {
        account = Account.builder()
                .productId(123456)
                .accountHolderFisrtname("Name")
                .accountHolderLastname("Last Name")
                .build();
        card = Card.builder()
                .cardId("123456" + Utils.randomNumeric((short) 10))
                .accountId(account.getProductId())
                .validThru(LocalDate.of(2023, 5, 26))
                .currency("USD")
                .balance(BigDecimal.ZERO)
                .active(false)
                .build();
    }

    @Test
    public void shouldIssueCard() {
        when(accountRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(account));
        when(cardRepository.save(ArgumentMatchers.any())).thenReturn(Mono.just(card));
        Mono<Card> result = issueCardUseCase.issueCard(123456);
        StepVerifier
                .create(result)
                .expectNext(card)
                .verifyComplete();
    }

    @Test
    public void shouldIssueCardPaddingCardNumber() {
        when(accountRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Mono.just(account));
        when(cardRepository.save(ArgumentMatchers.any())).thenReturn(Mono.just(card));
        Mono<Card> result = issueCardUseCase.issueCard(1);
        StepVerifier
                .create(result)
                .consumeNextWith(newCard -> {
                    assertEquals(16, newCard.getCardId().length());
                })
                .verifyComplete();
    }
}
