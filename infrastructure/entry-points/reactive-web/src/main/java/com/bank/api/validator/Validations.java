package com.bank.api.validator;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.bank.api.dto.AccountHolder;
import com.bank.api.dto.Balance;
import com.bank.api.exception.InputException;

public class Validations {

    private static final Validator accountHolderValidator = new AccountHolderValidator();

    private static final Validator balanceValidator = new BalanceValidator();

    public static void validate(AccountHolder accountHolder) {
        Errors errors = new BeanPropertyBindingResult(accountHolder, "accountHolder");
        accountHolderValidator.validate(accountHolder, errors);
        if (errors.hasErrors()) {
            throw new InputException("Some input field are required", errors.getFieldErrors().toString());
        }
    }

    public static void validate(Balance balance) {
        Errors errors = new BeanPropertyBindingResult(balance, "balance");
        balanceValidator.validate(balance, errors);
        if (errors.hasErrors()) {
            throw new InputException("Invalid balance input", errors.getFieldErrors().toString());
        }
    }
}
