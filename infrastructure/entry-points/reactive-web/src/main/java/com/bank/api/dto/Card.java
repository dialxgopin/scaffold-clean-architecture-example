package com.bank.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Card {
	private String cardId;
	@JsonFormat(pattern = "MM/yyyy")
	private LocalDate validThru;
	private String currency;
	private BigDecimal balance;
	private boolean active;

	public static Card fromCardModel(com.bank.model.card.Card card) {
		return Card.builder()
				.cardId(card.getCardId())
				.validThru(card.getValidThru())
				.currency(card.getCurrency())
				.balance(card.getBalance())
				.active(card.isActive())
				.build();
	}
}
