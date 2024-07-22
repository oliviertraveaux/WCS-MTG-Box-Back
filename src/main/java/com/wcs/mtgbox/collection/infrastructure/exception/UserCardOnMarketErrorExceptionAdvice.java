package com.wcs.mtgbox.collection.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class UserCardOnMarketErrorExceptionAdvice {
    @ExceptionHandler(UserCardOnMarketErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String MarketCardErrorException(UserCardOnMarketErrorException ex) {
        return ex.getMessage();
    }
}
