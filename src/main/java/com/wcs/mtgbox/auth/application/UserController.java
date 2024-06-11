package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UpdateUserDTO;
import com.wcs.mtgbox.auth.domain.service.userInfo.UserInfoService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
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
    ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody String newUsername, HttpServletRequest request, HttpServletResponse response) {
        return userInfoService.updateUsername(id, newUsername, request, response);
    }

    @PutMapping("/update-password/{id}")
    ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String newPassword, HttpServletRequest request) {
        return userInfoService.updatePassword(id, newPassword, request);
    }

    @PutMapping("/update-infos/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserRequest, HttpServletRequest request) {
        return userInfoService.updateUser(id, updateUserRequest, request);
    }

    @PostMapping("/verify-password/{id}")
    public ResponseEntity<?> verifyPassword(@PathVariable Long id, @RequestBody String password, HttpServletRequest request) {
        return userInfoService.verifyPassword(id, password, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return userInfoService.deleteUser(id, response);
    }
}
