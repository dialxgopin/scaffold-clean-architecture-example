package com.bank.api.exceptionhandler;

import java.util.function.BiFunction;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bank.api.dto.ErrorResponse;
import com.bank.api.exception.InputException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class InputExceptionHandler {

    public static BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> {
            InputException exception = (InputException) err;
            ErrorResponse response = new ErrorResponse();
            response.setCode(exception.getErrorCode());
            response.setMessage(exception.getMessage());
            response.setReason(exception.getReason());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
