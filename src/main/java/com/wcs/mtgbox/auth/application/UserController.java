package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UpdateUserDTO;
import com.wcs.mtgbox.auth.domain.service.userInfo.UserInfoService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "User management", description = "API to manage users")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Read user information", description = "Returns the information of a user based on their ID")

    ResponseEntity<?> readOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userInfoService.GetUser(id));
        } catch (Exception e) {
            throw new UserNotFoundErrorException(id);
        }
    }

    @PutMapping("/update-username/{id}")
    @Operation(summary = "Update username", description = "Updates the username based on their ID")

    ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody String newUsername, HttpServletRequest request, HttpServletResponse response) {
        return userInfoService.updateUsername(id, newUsername, request, response);
    }

    @PutMapping("/update-password/{id}")
    @Operation(summary = "Update password", description = "Updates the password based on their ID")

    ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String newPassword, HttpServletRequest request) {
        return userInfoService.updatePassword(id, newPassword, request);
    }

    @PutMapping("/update-infos/{id}")
    @Operation(summary = "Update user information", description = "Updates the user information based on their ID")

    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserRequest, HttpServletRequest request) {
        return userInfoService.updateUser(id, updateUserRequest, request);
    }

    @PostMapping("/verify-password/{id}")
    @Operation(summary = "Verify password", description = "Verifies the password based on their ID")

    public ResponseEntity<?> verifyPassword(@PathVariable Long id, @RequestBody String password, HttpServletRequest request) {
        return userInfoService.verifyPassword(id, password, request);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur en fonction de son ID")

    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return userInfoService.deleteUser(id, response);
    }
}
