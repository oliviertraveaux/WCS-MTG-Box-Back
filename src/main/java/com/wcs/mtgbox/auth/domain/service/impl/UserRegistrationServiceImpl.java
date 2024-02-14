package com.wcs.mtgbox.auth.domain.service.impl;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserRegistrationServiceImpl implements com.wcs.mtgbox.auth.domain.service.UserRegistrationService {
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
            return userMapper.transformUserEntityInUserDto(user, true);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("user or email already exists");
        }
    }

    @Override
    public String GenerateHashedPassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }


    // Pour test route "/users/{id}" à delete plus tard
    @Override
    public UserDTO GetUser(Long userId) {
        try {
            return userMapper.transformUserEntityInUserDtoTest(userRepository.findById(userId), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
