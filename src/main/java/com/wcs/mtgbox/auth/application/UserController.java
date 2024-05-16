package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UpdateUserDTO;
import com.wcs.mtgbox.auth.domain.service.userInfo.UserInfoService;
import com.wcs.mtgbox.auth.domain.service.userInfo.UserService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserInfoService userInfoService;
    private final UserService userService;

    public UserController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userInfoService.GetUser(id));
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }

    @PutMapping("/update-username/{id}")
    ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody String newUsername) {
        try {
            userService.updateUsername(id, newUsername);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }

    @PutMapping("/update-password/{id}")
    ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        try {
            userService.updatePassword(id, newPassword);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }


    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserRequest) {
        try {
            userService.updateUser(id, updateUserRequest);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundErrorException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/verify-password/{id}")
    public ResponseEntity<?> verifyPassword(@PathVariable Long id, @RequestBody String password) {
        try {
            boolean isPasswordValid = userService.verifyPassword(id, password);
            return ResponseEntity.ok(isPasswordValid);
        } catch (UserNotFoundErrorException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}

