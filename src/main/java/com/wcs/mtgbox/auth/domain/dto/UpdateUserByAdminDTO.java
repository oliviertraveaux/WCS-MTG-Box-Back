package com.wcs.mtgbox.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserByAdminDTO {
    private Boolean isBanned;
    private RoleEnum role;
}
