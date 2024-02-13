package com.wcs.mtgbox.auth.domain.service;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;

public interface UserRegistrationService {

    UserDTO UserRegistration(UserRegistrationDTO userData);

    String GenerateHashedPassword(String password);

    UserDTO GetUser(Long userId);

    boolean isUsernameAndEmailAvailable(String username, String email);
}
