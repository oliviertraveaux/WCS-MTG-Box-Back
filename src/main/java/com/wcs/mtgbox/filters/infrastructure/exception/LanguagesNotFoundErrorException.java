package com.wcs.mtgbox.filters.infrastructure.exception;

public class LanguagesNotFoundErrorException extends RuntimeException {
    public LanguagesNotFoundErrorException() {
        super("Could not find languages");
    }
}
