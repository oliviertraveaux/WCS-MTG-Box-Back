package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CreateOfferBannedUserErrorExceptionAdvice {
    @ExceptionHandler(CreateOfferBannedUserErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String errorCreateOfferHandler(CreateOfferBannedUserErrorException ex) {return ex.getMessage();}
}
