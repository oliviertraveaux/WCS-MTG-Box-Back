package com.wcs.mtgbox.auth.infrastructure.repository;

import com.wcs.mtgbox.auth.domain.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {
    PasswordToken findByToken(String token);
}
