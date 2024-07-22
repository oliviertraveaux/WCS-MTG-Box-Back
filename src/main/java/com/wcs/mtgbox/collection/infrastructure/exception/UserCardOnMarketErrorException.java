package com.wcs.mtgbox.collection.infrastructure.exception;

public class UserCardOnMarketErrorException extends RuntimeException{
    public UserCardOnMarketErrorException() { super("Invalid request");}
}