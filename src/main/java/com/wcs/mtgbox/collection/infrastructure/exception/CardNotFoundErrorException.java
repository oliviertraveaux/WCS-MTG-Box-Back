package com.wcs.mtgbox.collection.infrastructure.exception;

public class CardNotFoundErrorException extends RuntimeException{
    public CardNotFoundErrorException() { super("Could not find card");}
    public CardNotFoundErrorException(Long id) { super("Could not find card with id " + id);}

}
