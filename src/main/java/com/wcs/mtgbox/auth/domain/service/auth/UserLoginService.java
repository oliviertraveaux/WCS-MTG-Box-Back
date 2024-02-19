package com.wcs.mtgbox.auth.domain.service.auth;

import com.wcs.mtgbox.auth.domain.entity.User;

public interface UserLoginService {

    User login(User user);

    boolean verifyHashedPasswordDuringLogin(String password, String hashedPassword);

    User getUserEntityByUsername(String username);
}
