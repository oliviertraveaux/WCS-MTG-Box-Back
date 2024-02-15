package com.wcs.mtgbox.auth.infrastructure.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundErrorExceptionAdvice {
    @ExceptionHandler(UserNotFoundErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorGetUserHandler(UserNotFoundErrorException ex) {
        return ex.getMessage();
    }
}
