package com.wcs.mtgbox.collection.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CardQualityNotFoundErrorExceptionAdvice {
    @ExceptionHandler(CardQualityNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetCardHandler(CardQualityNotFoundErrorException ex) {
        return ex.getMessage();
    }
}
