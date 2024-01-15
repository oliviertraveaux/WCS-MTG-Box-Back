package com.wcs.mtgbox.auth.domain.service;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.auth.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserMapper {

    public UserDTO transformUserEntityInUserDto(User user, Boolean isForResponse) {

            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            userDTO.setActive(user.getActive());
            userDTO.setBanned(user.getBanned());
            userDTO.setPostCode(user.getPostCode());
            userDTO.setCity(user.getCity());
            userDTO.setLastConnectionDate(user.getLastConnectionDate());
            userDTO.setCreationDate(user.getCreationDate());
            userDTO.setRole(user.getRole());
            userDTO.setPassword(isForResponse ? "" : user.getPassword());

            return userDTO;
    }

    public User transformUserRegistrationDtoInUserEntity(UserRegistrationDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPostCode(userDTO.getPostCode());
        user.setCity(userDTO.getCity());
        user.setRole(new Role(1L, "USER"));
        user.setPassword(userDTO.getPassword());

        return user;
    }

    // Pour test route "/users/{id}" à delete plus tard
    public UserDTO transformUserEntityInUserDtoTest(Optional<User> user, Boolean isForResponse) {
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
           userDTO.setPassword(isForResponse ? "" : userToReturn.getPassword());
        });

        return userDTO;
        }
}
