package com.bank.api.exception;

import lombok.Getter;

@Getter
public class InputException extends RuntimeException {

    private static final int errorCode = 1;
    private String message;
    private String reason;

    public InputException(String message, String reason) {
        super(message);
        this.message = message;
        this.reason = reason;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
