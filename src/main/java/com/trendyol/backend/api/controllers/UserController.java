package com.trendyol.backend.api.controllers;

import com.trendyol.backend.business.abstracts.UserService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.User;
import com.trendyol.backend.entities.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {

        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @GetMapping("/getUserByEmail")
    public DataResult<UserDto> getUserByEmail(@RequestParam("email") String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/getAllUser")
    public DataResult<List<UserDto>> getAllUser() {
        return this.userService.getAllUser();
    }

    @DeleteMapping("/deleteUserByEmail")
    public Result deleteUserByEmail(@RequestParam("email") String email) {
        return this.userService.deleteUserByEmail(email);
    }

    @PutMapping("/updateUserEmailById/{id}")
    public Result updateEmailUserById(@PathVariable("id") String id, @RequestBody User user) {
        Result result = this.userService.updateEmailUserById(user, id);
        return result;
    }


}
