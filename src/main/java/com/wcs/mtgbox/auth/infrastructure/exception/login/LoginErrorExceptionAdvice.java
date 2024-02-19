package com.wcs.mtgbox.auth.infrastructure.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginErrorExceptionAdvice {
    @ExceptionHandler(LoginErrorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String errorLoginHandler(LoginErrorException ex) {
        return ex.getMessage();
    }
}
