package com.bank.api;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bank.api.dto.AccountHolder;
import com.bank.api.dto.Balance;
import com.bank.model.account.Account;
import com.bank.model.card.Card;
import com.bank.usecase.accountcreation.AccountCreationUseCase;
import com.bank.usecase.checkbalance.CheckBalanceUseCase;
import com.bank.usecase.deposit.DepositUseCase;
import com.bank.usecase.issuecard.IssueCardUseCase;
import com.bank.usecase.spend.SpendUseCase;
import com.bank.usecase.togglecard.ToggleCardUseCase;

import reactor.core.publisher.Mono;

@ContextConfiguration(classes = { RouterRest.class, Handler.class })
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AccountCreationUseCase accountCreationUseCase;

    @MockBean
    private IssueCardUseCase issueCardUseCase;

    @MockBean
    private ToggleCardUseCase toggleCardUseCase;

    @MockBean
    private DepositUseCase depositUseCase;

    @MockBean
    private CheckBalanceUseCase checkBalanceUseCase;

    @MockBean
    private SpendUseCase spendUseCase;

    private final static Account account = new Account();
    private final static Card card = new Card();

    @Test
    void testGenerateCardNumber() {
        when(issueCardUseCase.issueCard(ArgumentMatchers.anyInt()))
            .thenReturn(Mono.just(card));
        webTestClient.get()
                .uri("/card/issue/{productId}",123)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(com.bank.api.dto.Card.class);
    }

    @Test
    void testCreateAccount() {
        when(accountCreationUseCase.createAccount(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(account));
        AccountHolder accountHolder = new AccountHolder("Aa", "Bb");
        webTestClient.post()
                .uri("/account")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(accountHolder)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class);
    }

    @Test
    void testCreateAccountMissingRequiredField() {
        when(accountCreationUseCase.createAccount(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(account));
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setAccountHolderFisrtname("Ab");
        webTestClient.post()
                .uri("/account")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(accountHolder)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(Account.class);
    }

    @Test
    void testActivateCard() {
        when(toggleCardUseCase.activateCard(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(card));
        webTestClient.post()
                .uri("/card/activate/{cardId}",123)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(com.bank.api.dto.Card.class);
    }

    @Test
    void testDeactivateCard() {
        when(toggleCardUseCase.deactivateCard(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(card));
        webTestClient.delete()
                .uri("/card/deactivate/{cardId}",123)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(com.bank.api.dto.Card.class);
    }

    @Test
    void testDeposit() {
        when(depositUseCase.deposit(ArgumentMatchers.anyString(),
            ArgumentMatchers.any(BigDecimal.class)))
            .thenReturn(Mono.just(card));
        Balance balance = Balance.builder()
            .cardId("1234567890")
            .balance(BigDecimal.valueOf(841651.0))
            .build();
        webTestClient.post()
                .uri("/card/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(balance)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Balance.class);
    }

    @Test
    void testDepositShouldBeGreaterThanZero() {
        when(depositUseCase.deposit(ArgumentMatchers.anyString(),
            ArgumentMatchers.any(BigDecimal.class)))
            .thenReturn(Mono.just(card));
        Balance balance = Balance.builder()
            .cardId("1234567890")
            .balance(BigDecimal.ONE.negate())
            .build();
        webTestClient.post()
                .uri("/card/deposit")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(balance)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(Balance.class);
    }

    @Test
    void testCheckbalance() {
        when(checkBalanceUseCase.checkBalance(ArgumentMatchers.anyString()))
            .thenReturn(Mono.just(new Card()));
        webTestClient.get()
                .uri("/card/balance/{cardId}",123)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Balance.class);
    }

    @Test
    void testSpend() {
        when(spendUseCase.spend(ArgumentMatchers.anyString(),
            ArgumentMatchers.any(BigDecimal.class)))
            .thenReturn(Mono.just(new Card()));
        Balance balance = Balance.builder()
            .cardId("1234567890")
            .balance(BigDecimal.valueOf(999999.9))
            .build();
        webTestClient.post()
                .uri("/card/spend")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(balance)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Balance.class);
    }
}
