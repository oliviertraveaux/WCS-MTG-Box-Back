package com.wcs.mtgbox.auth.domain.service.auth;

import com.wcs.mtgbox.auth.domain.entity.Token;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtTokenService {
    Token generateToken(UserDetails userDetails);

    String getUsernameFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    Date extractExpiration(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    ResponseCookie createJwtCookie(String tokenValue);

    void deleteCookie(HttpServletResponse response, String cookieName);

    ResponseEntity<?> verifyToken(HttpServletRequest request);

    void reloadJwtCookie(HttpServletResponse response, UserDetails userDetails);


}
