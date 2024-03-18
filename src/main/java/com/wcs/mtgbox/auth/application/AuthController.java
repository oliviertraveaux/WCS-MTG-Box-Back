package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Token;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.domain.service.auth.UserLoginService;
import com.wcs.mtgbox.auth.domain.service.auth.UserRegistrationService;
import com.wcs.mtgbox.auth.domain.service.auth.impl.UserMapper;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final UserRegistrationService userRegistrationService;
    private final UserRepository userRepository;
    public UserMapper userMapper = new UserMapper();

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            UserDetailsService userDetailsService,
            UserRegistrationService userRegistrationService,
            UserRepository userRepository) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {

            userLoginService.login(userBody);
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getUsername()));
            ResponseCookie jwtCookie = jwtTokenService.createJwtCookie(token.getToken());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .build();
    }


    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userBody)  {
            return ResponseEntity.status(201).body(userRegistrationService.UserRegistration(userBody));//
    }

    @GetMapping("/api/v1/check-availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(@RequestParam String username, @RequestParam String email) {
        boolean isAvailable = userRegistrationService.isUsernameAndEmailAvailable(username, email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        jwtTokenService.deleteCookie(response, "token");
        return ResponseEntity.ok().build();
    }


    @GetMapping("/api/v1/verify-token")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        return jwtTokenService.verifyToken(request);
    }

}



