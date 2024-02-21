package com.wcs.mtgbox.filters.infrastructure.exception;

public class RaritiesNotFoundErrorException extends RuntimeException {
    public RaritiesNotFoundErrorException() {
        super("Could not find rarities");
    }
}
