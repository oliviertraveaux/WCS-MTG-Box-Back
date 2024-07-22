package com.wcs.mtgbox.auth.infrastructure.exception.registration;

public class PasswordTokenException extends RuntimeException{
    public PasswordTokenException() {
        super("Token is not valid");
    }
}
