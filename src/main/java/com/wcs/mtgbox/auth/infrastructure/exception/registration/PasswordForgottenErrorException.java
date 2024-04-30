package com.wcs.mtgbox.auth.infrastructure.exception.registration;


public class PasswordForgottenErrorException extends RuntimeException {
    public PasswordForgottenErrorException(String message) {
        super(message);
    }

}
