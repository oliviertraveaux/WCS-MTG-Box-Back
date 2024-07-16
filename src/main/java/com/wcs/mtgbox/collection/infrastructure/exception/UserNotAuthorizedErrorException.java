package com.wcs.mtgbox.collection.infrastructure.exception;

public class UserNotAuthorizedErrorException extends RuntimeException{
    public UserNotAuthorizedErrorException () { super("User is not authorized to modify this collection");}
}
