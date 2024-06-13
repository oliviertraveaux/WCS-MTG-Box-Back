package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.service.auth.impl.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/login",
            "/api/v1/register",
            "/api/v1/check-availability*",
            "/api/v1/filters",
            "/api/v1/filters/**",
            "/api/v1/filters/**",
            "/api/v1/logout",
            "/api/v1/password-forgotten/**",
            "/api/v1/new-password/**",
            "/api/v1/marketcards",
            "/api/v1/marketcards/**",
            "/api/v1/card-ad/**",
            "/api/v1/offer/card-ad/**",
            "/api/v1/verify-token",
            "/api/v1/verify-token",
            "/swagger-ui/**",
            "/v3/api-docs/**"

    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(
                                "/api/v1/users/**",
                                "/api/v1/apicards",
                                "/api/v1/apicards/**",
                                "/api/v1/collection-cards",
                                "/api/v1/collection-cards/**",
                                "/api/v1/upload",
                                "/api/v1/upload/**",
                                "api/v1/logout",
                                "/files",
                                "/api/v1/offer/",
                                "/api/v1/offer/**"
                        ).authenticated()
                )
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // You can disable csrf protection by removing this line
                        .ignoringRequestMatchers("/api/v1/register",
                                "/api/v1/login",
                                "/api/v1/register",
                                "/api/v1/users/**",
                                "/api/v1/apicards/**",
                                "/api/v1/collection-cards/**",
                                "/api/v1/upload/**",
                                "/files",
                                "/api/v1/verify-token",
                                "/api/v1/logout",
                                "/api/v1/password-forgotten/**",
                                "/api/v1/new-password/**",
                                "/api/v1/marketcards",
                                "/api/v1/marketcards/**",
                                "/api/v1/offer/",
                                "/api/v1/offer/**",
                                "/api/v1/card-ad/**",
                                "/uploads")
                        .disable()  // Décommentez pour désactiver en entier la protection CSRF en développement
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
                );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
