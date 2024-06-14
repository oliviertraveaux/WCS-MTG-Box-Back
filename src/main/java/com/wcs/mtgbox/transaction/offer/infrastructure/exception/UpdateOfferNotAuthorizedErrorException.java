package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

public class UpdateOfferNotAuthorizedErrorException extends RuntimeException{
    public UpdateOfferNotAuthorizedErrorException() {
        super("You are not authorized to update this offer.");
    }
}
