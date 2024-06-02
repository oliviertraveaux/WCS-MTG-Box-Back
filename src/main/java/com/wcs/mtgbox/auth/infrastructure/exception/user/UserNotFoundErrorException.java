package com.wcs.mtgbox.auth.infrastructure.exception.user;

public class UserNotFoundErrorException extends RuntimeException {
    public UserNotFoundErrorException() {
        super("Could not find user");
    }
    public UserNotFoundErrorException(Long id) {
        super("Could not find user with id " + id);
    }

    public UserNotFoundErrorException(String tokenNotFound) {
    }
}
