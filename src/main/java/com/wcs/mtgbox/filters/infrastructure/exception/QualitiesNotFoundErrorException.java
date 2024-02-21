package com.wcs.mtgbox.filters.infrastructure.exception;

public class QualitiesNotFoundErrorException extends RuntimeException {
    public QualitiesNotFoundErrorException() {
        super("Could not find qualities");
    }
}
