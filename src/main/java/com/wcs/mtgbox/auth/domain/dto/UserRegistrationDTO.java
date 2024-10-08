package com.wcs.mtgbox.auth.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private int Department;
    private String city;

    public UserRegistrationDTO(String username, String email, String password, int Department, String city) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.Department =Department;
        this.city = city;
    }

}
