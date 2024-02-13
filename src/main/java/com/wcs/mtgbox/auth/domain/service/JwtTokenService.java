package com.wcs.mtgbox.auth.domain.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtTokenService {
    String generateToken(UserDetails userDetails);

    String getUsernameFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    Date extractExpiration(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
