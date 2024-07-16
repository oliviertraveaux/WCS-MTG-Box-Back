package com.wcs.mtgbox.collection.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotAuthorizedErrorExceptionAdvice {
    @ExceptionHandler(UserNotAuthorizedErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String UserNotAuthorizedErrorException(UserNotAuthorizedErrorException ex) {return ex.getMessage();}
}
