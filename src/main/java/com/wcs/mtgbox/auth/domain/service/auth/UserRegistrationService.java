package com.wcs.mtgbox.auth.domain.service.auth;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;

public interface UserRegistrationService {

    UserDTO UserRegistration(UserRegistrationDTO userData);

    String GenerateHashedPassword(String password);

    boolean isUsernameAndEmailAvailable(String username, String email);
}
