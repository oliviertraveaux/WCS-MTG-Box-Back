package com.wcs.mtgbox.auth.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserDTO {
    private String email;
    private Integer department;
    private String city;
    private static Boolean isActive;

    public static Boolean getActive() {
        return isActive;
    }

}
