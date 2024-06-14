package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UpdateOfferNotAuthorizedErrorExceptionAdvice {
    @ExceptionHandler(UpdateOfferNotAuthorizedErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String errorModifyOfferHandler(UpdateOfferNotAuthorizedErrorException ex) { return ex.getMessage(); }
}
