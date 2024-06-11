package com.wcs.mtgbox.auth.domain.service.userInfo;

import com.wcs.mtgbox.auth.domain.dto.UpdateUserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserInfoService {
    UserDTO GetUser(Long userId);
    ResponseEntity<?> readOne(Long id, HttpServletRequest request);
    ResponseEntity<?> updateUsername(Long id, String newUsername, HttpServletRequest request, HttpServletResponse response);
    ResponseEntity<?> updatePassword(Long id, String newPassword, HttpServletRequest request);
    ResponseEntity<?> updateUser(Long id, UpdateUserDTO updateUserRequest, HttpServletRequest request);
    ResponseEntity<?> verifyPassword(Long id, String password, HttpServletRequest request);
    ResponseEntity<?> deleteUser(Long userId, HttpServletResponse response);
}

