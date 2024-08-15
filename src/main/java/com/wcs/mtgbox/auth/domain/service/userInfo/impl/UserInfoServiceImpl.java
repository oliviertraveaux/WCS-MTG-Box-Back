package com.wcs.mtgbox.auth.domain.service.userInfo.impl;

import com.wcs.mtgbox.auth.domain.dto.*;
import com.wcs.mtgbox.auth.domain.entity.Role;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.domain.service.auth.impl.UserDetailsServiceImpl;
import com.wcs.mtgbox.auth.domain.service.auth.impl.UserMapper;
import com.wcs.mtgbox.auth.domain.service.userInfo.UserInfoService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.collection.infrastructure.repository.UserCardRepository;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final OfferService offerService;

    public UserInfoServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService,
            UserCardRepository userCardRepository, OfferService offerService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userCardRepository = userCardRepository;
        this.offerService = offerService;
    }

    @Override
    public UserDTO GetUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundErrorException(userId));
        return userMapper.transformUserEntityInUserDto(Optional.ofNullable(user));
    }

    @Override
    public ResponseEntity<?> readOne(Long id, HttpServletRequest request) {
        if (!isAuthorized(request, id)) {
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
        if (!isAuthorized(request, id)) {
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
        if (!isAuthorized(request, id)) {
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
        if (!isAuthorized(request, id)) {
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
            if (updateUserRequest.getIsActive() != null) {
                user.setIsActive(updateUserRequest.getIsActive());
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
        if (!isAuthorized(request, id)) {
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
    public ResponseEntity<?> deleteUser(Long userId, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!isAuthorized(request, userId)) {
                return ResponseEntity.status(403).body("You are not authorized to delete this user.");
            }

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
    private boolean isAuthorized(HttpServletRequest request, Long id) {
        return jwtTokenService.getUserIdFromToken(request).equals(id);
    }

    @Override
    public UserDTO administrateUser(Long id, UpdateUserByAdminDTO request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundErrorException(id));

        boolean dataChanged = false;

        if (request.getIsBanned() != null && !request.getIsBanned().equals(user.getIsBanned())) {
            user.setIsBanned(request.getIsBanned());
            dataChanged = true;
        }

        if (user.getIsBanned()) {
            this.offerService.deleteAllByUserId(id);
        }

        if (request.getRole() != null && !request.getRole().equals(user.getRole().getType())) {
            Long roleId = request.getRole().equals(RoleEnum.USER) ? 1L : 2L;
            user.setRole(new Role(roleId, request.getRole()));
            dataChanged = true;
        }

        if (dataChanged) {
            userRepository.save(user);
        }

        return userMapper.transformUserEntityInUserDto(Optional.of(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.transformUserEntityInUserDto(Optional.of(user)))
                .toList();
    }
}
