package com.wcs.mtgbox.auth.domain;

import com.wcs.mtgbox.auth.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserSetName() {
        String userName = "John";
        user.setUsername(userName);
        assertEquals(userName, user.getUsername());
    }
}
