package com.wcs.mtgbox.auth.domain.service;

import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    public UserLoginService(
            UserRepository userRepository,
            BCryptPasswordEncoder bcryptPasswordEncoder
    ) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    public User login(User user) {
        User userEntity = getUserEntityByUsername(user.getUsername());
        if (!verifyHashedPasswordDuringLogin(user.getPassword(), userEntity.getPassword())){
            throw new BadCredentialsException("Wrong username or password");
        }
        user.setRole(userEntity.getRole());

        return user;
    }

    public boolean verifyHashedPasswordDuringLogin(String password, String hashedPassword) {
        return bcryptPasswordEncoder.matches(password, hashedPassword);
    }

    public User getUserEntityByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}