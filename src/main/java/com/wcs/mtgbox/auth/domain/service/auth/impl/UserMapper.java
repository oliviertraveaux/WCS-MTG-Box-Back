package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserMapper {

    public User transformUserRegistrationDtoInUserEntity(UserRegistrationDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPostCode(userDTO.getPostCode());
        user.setCity(userDTO.getCity());
        user.setRole(new Role(1L, "USER"));
        user.setActive(true);
        user.setBanned(false);
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public UserDTO transformUserEntityInUserDto(Optional<User> user) {
        if (user.isEmpty()) {
            throw new UserNotFoundErrorException();
        }
        UserDTO userDTO = new UserDTO();
        user.ifPresent(userToReturn -> {
           userDTO.setId(userToReturn.getId());
           userDTO.setEmail(userToReturn.getEmail());
           userDTO.setUsername(userToReturn.getUsername());
           userDTO.setActive(userToReturn.getActive());
           userDTO.setBanned(userToReturn.getBanned());
           userDTO.setPostCode(userToReturn.getPostCode());
           userDTO.setCity(userToReturn.getCity());
           userDTO.setLastConnectionDate(userToReturn.getLastConnectionDate());
           userDTO.setCreationDate(userToReturn.getCreationDate());
           userDTO.setRole(userToReturn.getRole());
        });
        return userDTO;
        }
}
