package com.wcs.mtgbox.auth.application;

import com.wcs.mtgbox.auth.domain.dto.UserDTO;
import com.wcs.mtgbox.auth.domain.service.UserRegistrationService;
import org.springframework.web.bind.annotation.*;

// Pour test route "/users/{id}" à delete plus tard
@RestController
@RequestMapping("/users")
public class UserController {
    private UserRegistrationService userRegistrationService;

    public UserController(
            UserRegistrationService userRegistrationService
    ) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/{id}")
    UserDTO readOne(@PathVariable Long id) throws Exception{
        System.out.println("msg");
        return userRegistrationService.GetUser(id);
    }
}
