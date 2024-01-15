package com.wcs.mtgbox.auth.infrastructure.repository;

import com.wcs.mtgbox.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}