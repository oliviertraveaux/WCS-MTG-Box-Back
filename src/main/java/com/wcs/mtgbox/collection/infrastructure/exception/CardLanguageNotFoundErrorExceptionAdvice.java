package com.wcs.mtgbox.collection.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CardLanguageNotFoundErrorExceptionAdvice {
    @ExceptionHandler(CardLanguageNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetCardHandler(CardLanguageNotFoundErrorException ex) {
        return ex.getMessage();
    }
}
