package com.bank.model.account;
import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Account {
    private Integer productId;
    private String accountHolderFisrtname;
	private String accountHolderLastname;
}
