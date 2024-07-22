package com.wcs.mtgbox.auth.infrastructure.exception.registration;

public class RegistrationErrorException extends RuntimeException {
    public RegistrationErrorException(String message) {
        super(message);
    }
}
