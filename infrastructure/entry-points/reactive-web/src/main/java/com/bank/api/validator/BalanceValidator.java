package com.bank.api.validator;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bank.api.dto.Balance;

public class BalanceValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Balance.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "cardId",
                "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "balance",
                "field.required");
        Balance balance = (Balance) target;
        if (BigDecimal.ZERO.compareTo(balance.getBalance() == null ? BigDecimal.ZERO : balance.getBalance()) >= 0) {
            errors.rejectValue("balance", "must be greater than zero");
        }
    }

}
