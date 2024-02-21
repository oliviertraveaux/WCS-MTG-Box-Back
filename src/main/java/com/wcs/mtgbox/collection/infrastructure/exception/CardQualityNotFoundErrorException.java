package com.wcs.mtgbox.collection.infrastructure.exception;

public class CardQualityNotFoundErrorException extends RuntimeException{
    public CardQualityNotFoundErrorException() { super("Could not find quality");}
    public CardQualityNotFoundErrorException(Long id) { super("Could not find quality with id " + id);}

}
