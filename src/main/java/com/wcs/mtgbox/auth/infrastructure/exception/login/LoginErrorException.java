package com.wcs.mtgbox.auth.infrastructure.exception.login;

public class LoginErrorException extends RuntimeException {
    public LoginErrorException(String message) {
        super(message);
    }
}
