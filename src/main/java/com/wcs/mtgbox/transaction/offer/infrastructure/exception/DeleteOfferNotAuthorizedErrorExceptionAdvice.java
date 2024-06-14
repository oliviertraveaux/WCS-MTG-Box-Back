package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeleteOfferNotAuthorizedErrorExceptionAdvice {
    @ExceptionHandler(DeleteOfferNotAuthorizedErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String errorModifyOfferHandler(DeleteOfferNotAuthorizedErrorException ex) { return ex.getMessage(); }
}
