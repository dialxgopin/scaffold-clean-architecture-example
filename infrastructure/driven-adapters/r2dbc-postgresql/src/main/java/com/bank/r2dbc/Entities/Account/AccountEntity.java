package com.bank.r2dbc.Entities.Account;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("account")
public class AccountEntity {
    @Id
    private Integer productId;
    private String accountHolderFisrtname;
	private String accountHolderLastname;
}
