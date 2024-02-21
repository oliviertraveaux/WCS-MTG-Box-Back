package com.wcs.mtgbox.filters.infrastructure.exception;

public class SetsNotFoundErrorException extends RuntimeException {
    public SetsNotFoundErrorException() {
        super("Could not find sets");
    }
}
