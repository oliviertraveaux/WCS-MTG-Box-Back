package com.wcs.mtgbox.filters.infrastructure.exception;

public class TypesNotFoundErrorException extends RuntimeException {
    public TypesNotFoundErrorException() {
        super("Could not find types");
    }
}
