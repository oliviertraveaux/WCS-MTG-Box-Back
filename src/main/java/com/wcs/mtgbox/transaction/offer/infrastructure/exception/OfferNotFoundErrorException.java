package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

public class OfferNotFoundErrorException extends RuntimeException{
    public OfferNotFoundErrorException() {
        super("Could not find offer");
    }
    public OfferNotFoundErrorException(Long id) {
        super("Could not find offer with id " + id);
    }
}
