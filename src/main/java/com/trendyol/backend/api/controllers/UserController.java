package com.trendyol.backend.api.controllers;

import com.trendyol.backend.business.abstracts.UserService;
import com.trendyol.backend.business.concretes.UserManager;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.SuccessDataResult;
import com.trendyol.backend.dataAccsess.abstracts.UserDao;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/api/v1")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public DataResult<User> createUser(@RequestBody User user) {
        return this.userService.add(user);
    }

    @GetMapping("/getUserById/{id}")
    public DataResult<UserDto> getUserById(@PathVariable("id") String id) {

        return this.userService.getUserById(id);
    }

    @GetMapping("/getUserByEmail")
    public DataResult<UserDto> getUserByEmail(@RequestParam("email") String email) {
        return this.userService.getUserByEmail(email);
    }

}
