package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

public class DeleteOfferNotAuthorizedErrorException extends RuntimeException{
    public DeleteOfferNotAuthorizedErrorException() {
        super("You are not authorized to delete this offer.");
    }
}
