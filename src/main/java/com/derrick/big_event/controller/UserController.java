package com.derrick.big_event.controller;

import com.derrick.big_event.pojo.Result;
import com.derrick.big_event.pojo.User;
import com.derrick.big_event.service.UserService;
import com.derrick.big_event.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // Check if user exist
        User user = userService.findByUserName(username);
        if (user == null) {
            // Register new user
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("Username already in use");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // Check if user exist
        User user = userService.findByUserName(username);
        if (user != null) {
            // Check if user password is correct
            if (Md5Util.checkPassword(password, user.getPassword())) {
                return Result.success("JWT Token");
            }
        }

        return Result.error("Invalid username or password");
    }
}
