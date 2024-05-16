package com.wcs.mtgbox.auth.domain.dto;

import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.files.domain.entity.Media;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean isActive;
    private Boolean isBanned;
    private int department;
    private String city;
    private LocalDateTime lastConnectionDate;
    private LocalDateTime creationDate;
    private Role role;
    private Media avatar;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, Boolean isActive, Boolean isBanned, int department, String city, LocalDateTime lastConnectionDate, LocalDateTime creationDate, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isActive = isActive;
        this.isBanned = isBanned;
        this.department = department;
        this.city = city;
        this.lastConnectionDate = lastConnectionDate;
        this.creationDate = creationDate;
        this.role = role;
    }

}
