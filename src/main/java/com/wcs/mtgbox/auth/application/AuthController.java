package com.wcs.mtgbox.auth.application;


import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Token;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.domain.service.auth.PasswordForgottenService;
import com.wcs.mtgbox.auth.domain.service.auth.UserLoginService;
import com.wcs.mtgbox.auth.domain.service.auth.UserRegistrationService;

import com.wcs.mtgbox.auth.domain.service.auth.impl.UserMapper;
import com.wcs.mtgbox.auth.infrastructure.exception.registration.RegistrationErrorException;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Authentication", description = "API to handle user authentication and registration")
@RestController
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final UserRegistrationService userRegistrationService;
    private final PasswordForgottenService passwordForgottenService;
    public UserMapper userMapper = new UserMapper();

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            PasswordForgottenService passwordForgottenService,
            UserDetailsService userDetailsService,
            UserRegistrationService userRegistrationService,
            UserRepository userRepository) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
        this.passwordForgottenService = passwordForgottenService;
    }

    @PostMapping(value = "api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token")

    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {

        userLoginService.login(userBody);
        Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getUsername()));
        ResponseCookie jwtCookie = jwtTokenService.createJwtCookie(token.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }


    @PostMapping("/api/v1/register")
    @Operation(summary = "User registration", description = "Registers a new user")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userBody)  {
        return ResponseEntity.status(201).body(userRegistrationService.UserRegistration(userBody));//
    }

    @GetMapping("/api/v1/check-availability")
    @Operation(summary = "Check username and email availability", description = "Checks if a username and email are available for registration")


    public ResponseEntity<Map<String, Boolean>> checkAvailability(@RequestParam String username, @RequestParam String email) {
        boolean isAvailable = userRegistrationService.isUsernameAndEmailAvailable(username, email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/logout")
    @Operation(summary = "User logout", description = "Invalidates the user's JWT token and logs them out")


    public ResponseEntity<?> logout(HttpServletResponse response) {
        jwtTokenService.deleteCookie(response, "token");
        return ResponseEntity.ok().build();
    }


    @GetMapping("/api/v1/verify-token")
    @Operation(summary = "Verify JWT token", description = "Verifies the validity of a JWT token")

    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        return jwtTokenService.verifyToken(request);
    }

    @PostMapping("api/v1/password-forgotten/{email}")
    @Operation(summary = "Password forgotten", description = "Generates a token to allow the user to reset their password")

    public ResponseEntity<?> passwordForgotten(@PathVariable String email) throws RegistrationErrorException {
        try {
            return ResponseEntity.status(201).body(passwordForgottenService.tokenGenerator(email));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("api/v1/new-password/{token}")
    @Operation(summary = "Create new password", description = "Allows the user to create a new password using a valid token")

    public ResponseEntity<?> newPassword(@PathVariable String token, @RequestBody User userBody) {
        try {
            passwordForgottenService.checkTokenValidityAndCreateNewPassword(token, userBody);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


}
