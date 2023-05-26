package com.bank.r2dbc.Entities.Card;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("card")
public class CardEntity {
    @Id
	private Integer id;
    private String cardId;
	private Integer accountId;
	private LocalDate validThru;
	private String currency;
	private BigDecimal balance;
	private boolean active;
}
