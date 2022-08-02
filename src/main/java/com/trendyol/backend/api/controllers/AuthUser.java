package com.trendyol.backend.api.controllers;

import com.trendyol.backend.business.abstracts.AuthService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.entities.concretes.LoginUser;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.LoginUserDto;
import com.trendyol.backend.entities.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class AuthUser {
    private AuthService authService;

    @Autowired
    public AuthUser(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    DataResult<UserDto> createNewUser(@Valid @RequestBody User user) {
        DataResult<UserDto> user1 = this.authService.createUser(user);
        return user1;
    }

    @PostMapping("/login")
    DataResult<LoginUserDto> login(@RequestBody LoginUser user) {
        DataResult<LoginUserDto> login = this.authService.login(user);
        return login;
    }
}
