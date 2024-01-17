package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.service.UserRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

// Pour test route "/users/{id}" à delete plus tard
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserRegistrationService userRegistrationService;

    public UserController(
            UserRegistrationService userRegistrationService
    ) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readOne(@PathVariable Long id) throws Exception{
        try {
            return ResponseEntity.ok(userRegistrationService.GetUser(id));
        } catch (Exception e){
            return ResponseEntity.status(404).build();
        }
    }
}
