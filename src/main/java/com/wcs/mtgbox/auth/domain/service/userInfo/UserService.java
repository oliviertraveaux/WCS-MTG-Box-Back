package com.wcs.mtgbox.auth.domain.service.userInfo;

import com.wcs.mtgbox.auth.domain.dto.UpdateUserDTO;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateUsername(Long id, String newUsername) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void updateUser(Long id, UpdateUserDTO updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));

        boolean isUpdated = false;

        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
            isUpdated = true;
        }
        if (updateUserRequest.getDepartment() != null) {
            user.setDepartment(updateUserRequest.getDepartment());
            isUpdated = true;
        }
        if (updateUserRequest.getCity() != null) {
            user.setCity(updateUserRequest.getCity());
            isUpdated = true;
        }
        if (UpdateUserDTO.getActive() != null) {
            user.setIsActive(UpdateUserDTO.getActive());
            isUpdated = true;
        }

        if (isUpdated) {
            userRepository.save(user);
        }
    }
    public boolean verifyPassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundErrorException(userId));
        return passwordEncoder.matches(password, user.getPassword());
    }

}
