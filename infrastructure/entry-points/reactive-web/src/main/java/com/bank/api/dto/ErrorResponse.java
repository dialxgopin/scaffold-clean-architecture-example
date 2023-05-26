package com.bank.api.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private int code;
    private String message;
    private String reason;
}
