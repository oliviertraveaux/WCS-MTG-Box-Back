package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class OfferNotFoundErrorExceptionAdvice {
    @ExceptionHandler(OfferNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetOfferHandler(OfferNotFoundErrorException ex) { return ex.getMessage(); }
}
