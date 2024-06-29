package com.wcs.mtgbox.auth.infrastructure.exception.user;

public class UserAdministrationInvalidRoleErrorException extends RuntimeException {
    public UserAdministrationInvalidRoleErrorException()  {
        super("Role invalid");
    }
}