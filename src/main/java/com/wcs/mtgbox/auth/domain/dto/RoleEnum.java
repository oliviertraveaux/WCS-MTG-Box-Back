package com.wcs.mtgbox.auth.domain.dto;

import com.wcs.mtgbox.auth.infrastructure.exception.user.UserAdministrationInvalidRoleErrorException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String fullName;

    public static RoleEnum translateStringToRole(String role) {
        return switch (role.toLowerCase()) {
            case "user" -> RoleEnum.USER;
            case "admin" -> RoleEnum.ADMIN;
            default -> throw new UserAdministrationInvalidRoleErrorException();
        };
    }
}