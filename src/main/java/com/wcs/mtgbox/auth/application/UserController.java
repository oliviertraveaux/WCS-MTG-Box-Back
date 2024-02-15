package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.service.userInfo.UserInfoService;
import com.wcs.mtgbox.auth.infrastructure.exception.user.UserNotFoundErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserInfoService userInfoService;

    public UserController(
            UserInfoService userInfoService
    ) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userInfoService.GetUser(id));
        } catch (Exception e){
            throw new UserNotFoundErrorException(id);
        }
    }
}
