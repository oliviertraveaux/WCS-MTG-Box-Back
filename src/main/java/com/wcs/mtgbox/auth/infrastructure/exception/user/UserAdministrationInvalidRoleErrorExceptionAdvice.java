package com.wcs.mtgbox.auth.infrastructure.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAdministrationInvalidRoleErrorExceptionAdvice {
    @ExceptionHandler(UserAdministrationInvalidRoleErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String errorInvalidRole(UserAdministrationInvalidRoleErrorException ex) {
        return ex.getMessage();
    }
}
