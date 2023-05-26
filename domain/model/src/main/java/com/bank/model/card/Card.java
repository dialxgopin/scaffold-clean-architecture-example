package com.bank.model.card;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Card {
	private Integer id;
	private String cardId;
    private Integer accountId;
	private LocalDate validThru;
	private String currency;
	private BigDecimal balance;
	private boolean active;
}
