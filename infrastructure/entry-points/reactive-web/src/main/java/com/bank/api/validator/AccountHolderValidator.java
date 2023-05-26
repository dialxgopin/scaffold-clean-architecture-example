package com.bank.api.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bank.api.dto.AccountHolder;

public class AccountHolderValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountHolder.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "accountHolderFisrtname",
                "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,
                "accountHolderLastname",
                "field.required");
    }

}
