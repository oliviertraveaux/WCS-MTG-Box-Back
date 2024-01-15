package com.wcs.mtgbox.auth.domain.service;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserRegistrationService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    private UserMapper userMapper;

    public UserRegistrationService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO UserRegistration(UserRegistrationDTO userData) {
        userData.setPassword(GenerateHashedPassword(userData.getPassword()));
        try {
            User user = userRepository.save(userMapper.transformUserRegistrationDtoInUserEntity(userData));
            user = userRepository.findByUsername(user.getUsername());
            return userMapper.transformUserEntityInUserDto(user, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String GenerateHashedPassword(String password) {
        return bcryptPasswordEncoder.encode(password);
    }


    // Pour test route "/users/{id}" à delete plus tard
    public UserDTO GetUser(Long userId) {
        try {
            return userMapper.transformUserEntityInUserDtoTest(userRepository.findById(userId), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
