package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.JwtTokenService;
import com.wcs.mtgbox.auth.domain.service.UserDetailsServiceImpl;
import com.wcs.mtgbox.auth.domain.service.UserLoginService;
import com.wcs.mtgbox.auth.domain.service.UserRegistrationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;



@RestController
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private UserRegistrationService userRegistrationService;

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            UserDetailsServiceImpl userDetailsService,
            UserRegistrationService userRegistrationService
    ) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(@RequestBody User userBody)  {
        try {
            userLoginService.login(userBody);
            String token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getUsername()));

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userBody) throws Exception {
        try {
            return ResponseEntity.status(201).body(userRegistrationService.UserRegistration(userBody));
        }
        catch ( DataIntegrityViolationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/api/v1/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam String username, @RequestParam String email) {
        boolean isAvailable = userRegistrationService.isUsernameAndEmailAvailable(username, email);
        return ResponseEntity.ok(isAvailable);
    }
}


