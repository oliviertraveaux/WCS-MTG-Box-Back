package com.wcs.mtgbox.auth.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserByAdminRequestDTO {
    private Boolean isBanned;
    private String role;
}
