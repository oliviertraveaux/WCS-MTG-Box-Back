package com.wcs.mtgbox.transaction.offer.infrastructure.exception;

public class ValidateOfferErrorException extends RuntimeException{
    public ValidateOfferErrorException(String msg) {
        super("An error occurred while validating the offer: "+msg);
    }

}
