package com.wcs.mtgbox.auth.domain.service.userInfo;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;

public interface UserInfoService {
    UserDTO GetUser(Long userId);
}
