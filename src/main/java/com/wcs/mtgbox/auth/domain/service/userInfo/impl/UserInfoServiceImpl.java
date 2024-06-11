package com.wcs.mtgbox.auth.domain.service.userInfo.impl;

import com.wcs.mtgbox.auth.domain.dto.UpdateUserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.domain.service.auth.impl.UserDetailsServiceImpl;
import com.wcs.mtgbox.auth.domain.service.auth.impl.UserMapper;
import com.wcs.mtgbox.auth.domain.service.userInfo.UserInfoService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserCardRepository userCardRepository;


    public UserInfoServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService,
            UserCardRepository userCardRepository
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userCardRepository = userCardRepository;
    }

    @Override
    public UserDTO GetUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundErrorException(userId));
        return userMapper.transformUserEntityInUserDto(Optional.ofNullable(user));
    }

    @Override
    public ResponseEntity<?> readOne(Long id, HttpServletRequest request) {
        if (isAuthorized(request, id)) {
            return ResponseEntity.status(403).body("You are not authorized to view this user.");
        }
        try {
            return ResponseEntity.ok(userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundErrorException(id)));
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }

    @Override
    public ResponseEntity<?> updateUsername(Long id, String newUsername, HttpServletRequest request, HttpServletResponse response) {
        if (isAuthorized(request, id)) {
            return ResponseEntity.status(403).body("You are not authorized to update this user.");
        }
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));
            user.setUsername(newUsername);
            userRepository.save(user);

            // Recharge token jwt avec nouvel username
            UserDetails userDetails = userDetailsService.loadUserByUsername(newUsername);
            jwtTokenService.reloadJwtCookie(response, userDetails);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }

    @Override
    public ResponseEntity<?> updatePassword(Long id, String newPassword, HttpServletRequest request) {
        if (isAuthorized(request, id)) {
            return ResponseEntity.status(403).body("You are not authorized to update this user.");
        }
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }

    @Override
    public ResponseEntity<?> updateUser(Long id, UpdateUserDTO updateUserRequest, HttpServletRequest request) {
        if (isAuthorized(request, id)) {
            return ResponseEntity.status(403).body("You are not authorized to update this user.");
        }
        try {
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
            return ResponseEntity.ok().build();
        } catch (UserNotFoundErrorException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @Override
    public ResponseEntity<?> verifyPassword(Long id, String password, HttpServletRequest request) {
        if (isAuthorized(request, id)) {
            return ResponseEntity.status(403).body("You are not authorized to verify this user.");
        }
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));
            boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());
            return ResponseEntity.ok(isPasswordValid);
        } catch (UserNotFoundErrorException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteUser(Long userId, HttpServletResponse response) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // select username = deletedUser => doit etre présent en BDD
            User deletedUser = userRepository.findByUsername("deletedUser");
            if (deletedUser == null) {
                throw new RuntimeException("Deleted user entity not found");
            }


            List<UserCard> inactiveUserCards = user.getUserCards().stream()
                    .filter(userCard -> !userCard.getIsActive())
                    .collect(Collectors.toList());

            for (UserCard userCard : inactiveUserCards) {
                userCard.setUser(deletedUser);
            }
            userCardRepository.saveAll(inactiveUserCards);

            // Remove active uscards active = true
            List<UserCard> activeUserCards = user.getUserCards().stream()
                    .filter(UserCard::getIsActive)
                    .collect(Collectors.toList());

            userCardRepository.deleteAll(activeUserCards);
            
            userRepository.delete(user);

            jwtTokenService.deleteCookie(response, "token");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the user: " + e.getMessage());
        }
    }


    private Long getUserIdFromToken(HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token == null) {
            throw new UserNotFoundErrorException("Token not found");
        }

        String username = jwtTokenService.getUsernameFromToken(token);
        return userRepository.findByUsername(username)
                .getId();
    }

    private boolean isAuthorized(HttpServletRequest request, Long id) {
        return !getUserIdFromToken(request).equals(id);
    }
}
