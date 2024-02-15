package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UserRegistrationDTO;
import com.wcs.mtgbox.auth.domain.entity.Token;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.domain.service.auth.UserLoginService;
import com.wcs.mtgbox.auth.domain.service.auth.UserRegistrationService;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final UserLoginService userLoginService;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final UserRegistrationService userRegistrationService;

    public AuthController(
            UserLoginService userLoginService,
            JwtTokenService jwtTokenService,
            UserDetailsService userDetailsService,
            UserRegistrationService userRegistrationService
    ) {
        this.userLoginService = userLoginService;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping(value = "api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody User userBody) throws Exception {
        try {
            userLoginService.login(userBody);
            Token token = jwtTokenService.generateToken(userDetailsService.loadUserByUsername(userBody.getUsername()));

            ResponseCookie jwtCookie = ResponseCookie.from("token", token.getToken())
                    .httpOnly(true)   // Marquer le cookie comme HttpOnly pour la sécurité
                    //.secure(true)     // Marquer le cookie comme sécurisé (transmis uniquement via HTTPS)
                    .path("/")        // Le cookie est accessible pour l'ensemble du domaine
                    .maxAge(24 * 60 * 60) // Définir la durée de vie du cookie (exemple : 24 heures)
                    .sameSite("Strict") // Politique SameSite pour le cookie
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .build();
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

}
