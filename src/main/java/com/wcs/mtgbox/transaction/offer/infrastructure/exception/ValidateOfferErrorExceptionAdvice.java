package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidateOfferErrorExceptionAdvice {
    @ExceptionHandler(ValidateOfferErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String errorValidateOfferHandler(ValidateOfferErrorException ex) { return ex.getMessage(); }
}
