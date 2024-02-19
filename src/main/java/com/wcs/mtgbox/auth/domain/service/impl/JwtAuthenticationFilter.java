package com.wcs.mtgbox.auth.domain.service.impl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.util.Arrays;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenServiceImpl jwtTokenServiceImpl;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(
            JwtTokenServiceImpl jwtTokenServiceImpl,
            UserDetailsServiceImpl userDetailsService
    ) {
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void  doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String token;
        final String username;

        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        token = Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        username = jwtTokenServiceImpl.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("token: "+ token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenServiceImpl.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            }
        }
    }

}
