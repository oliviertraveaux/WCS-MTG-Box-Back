package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.UserRegistrationService;
import com.wcs.mtgbox.auth.infrastructure.exception.registration.RegistrationErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserRegistrationServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO UserRegistration(UserRegistrationDTO userData) {
        userData.setPassword(GenerateHashedPassword(userData.getPassword()));
        try {
            User user = userRepository.save(userMapper.transformUserRegistrationDtoInUserEntity(userData));
            user = userRepository.findByUsername(user.getUsername());
            return userMapper.transformUserEntityInUserDto(Optional.ofNullable(user));
        } catch (Exception e) {
            throw new RegistrationErrorException("user or email already exists");
        }

    }

    @Override
    public String GenerateHashedPassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }


    private boolean isUsernameUsed(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean isEmailUsed(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUsernameAndEmailAvailable(String username, String email) {
        boolean isUsernameUsed = this.isUsernameUsed(username);
        boolean isEmailUsed = this.isEmailUsed(email);
        return !isUsernameUsed && !isEmailUsed;
    }
}
