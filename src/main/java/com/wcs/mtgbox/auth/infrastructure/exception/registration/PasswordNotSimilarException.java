package com.wcs.mtgbox.auth.infrastructure.exception.registration;

public class PasswordNotSimilarException extends RuntimeException{
    public PasswordNotSimilarException() {
        super("Passwords are not similar");
    }
}
