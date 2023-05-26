package com.bank.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bank.api.exception.InputException;
import com.bank.api.exceptionhandler.InputExceptionHandler;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions.route()
                .GET("/card/issue/{productId}", handler::generateCardNumber)
                .POST("/account", handler::createAccount)
                .POST("/card/activate/{cardId}", handler::enrollCard)
                .DELETE("/card/deactivate/{cardId}", handler::lockCard)
                .POST("/card/deposit", handler::deposit)
                .GET("/card/balance/{cardId}", handler::checkBalance)
                .POST("/card/spend", handler::spend)
                .onError(InputException.class, InputExceptionHandler.exceptionHandler())
                .build();
    }
}
