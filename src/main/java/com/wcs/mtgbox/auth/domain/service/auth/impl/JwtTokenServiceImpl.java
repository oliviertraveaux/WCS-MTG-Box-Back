package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.entity.Token;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.auth.JwtTokenService;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.Integer.parseInt;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value( "${jwt.secretKey}" )
    private  String JWT_SECRET_KEY;

    @Value("${jwt.tokenValidityTimeInMinute}")
    public long JWT_TOKEN_VALIDITY_IN_MINUTE;

    @Override
    public Token generateToken(UserDetails userDetails) {
        Date now = new Date();
        Token token = new Token();
        token.setToken(Jwts.
                builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(now.getTime() + JWT_TOKEN_VALIDITY_IN_MINUTE * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact()
        );
        return token;
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                ;
    }

    @Override
    public Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public ResponseCookie createJwtCookie(String tokenValue) {
        return ResponseCookie.from("token", tokenValue)
                .httpOnly(true)
                //.secure(true)    // Décommenter pour marquer le cookie comme sécurisé (transmis uniquement via HTTPS)
                .path("/")
                .maxAge(JWT_TOKEN_VALIDITY_IN_MINUTE * 60)
                .sameSite("Strict")
                .build();
    }

    @Override
    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;



    @Override
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        String cookieName = "token";

        try {
            if (request.getCookies() == null) {
                throw new Exception("No cookies found");
            }

            String token = Arrays.stream(request.getCookies())
                    .filter(c -> cookieName.equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElseThrow(() -> new Exception("Token cookie not found"));

            String username = getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!isTokenValid(token, userDetails)) {
                throw new Exception("Invalid or expired token");
            }

            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new Exception("User not found");
            }

            UserDTO userDTO = userMapper.transformUserEntityInUserDto(Optional.of(user));
            return ResponseEntity.ok().body(userDTO);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("An error occurred while processing the token");
        }
    }


}

}
