package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

public class CreateOfferBannedUserErrorException extends RuntimeException {
    public CreateOfferBannedUserErrorException(){super("You are baned and can't create this offer.");}
}
