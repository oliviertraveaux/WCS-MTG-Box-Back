package com.wcs.mtgbox.collection.infrastructure.exception;

public class UserCardNotFoundErrorException extends RuntimeException{
    public UserCardNotFoundErrorException() { super("Could not find user card");}
    public UserCardNotFoundErrorException(Long id) { super("Could not find  user card with id " + id);}

}
