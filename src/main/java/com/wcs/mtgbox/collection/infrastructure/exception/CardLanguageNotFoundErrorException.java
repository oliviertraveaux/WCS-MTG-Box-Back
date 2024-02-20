package com.wcs.mtgbox.collection.infrastructure.exception;

public class CardLanguageNotFoundErrorException extends RuntimeException{
    public CardLanguageNotFoundErrorException() { super("Could not find language");}
    public CardLanguageNotFoundErrorException(Long id) { super("Could not find language with id " + id);}

}
