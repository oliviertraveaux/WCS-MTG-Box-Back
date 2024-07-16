package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

public class CreateOfferNotAuthorizedErrorException extends RuntimeException{
    public CreateOfferNotAuthorizedErrorException(){super("You are not authorized to create this offer.");}
}
