package com.derrick.big_event.controller;

import com.derrick.big_event.pojo.Result;
import com.derrick.big_event.pojo.User;
import com.derrick.big_event.service.UserService;
import com.derrick.big_event.utils.JwtUtil;
import com.derrick.big_event.utils.Md5Util;
import com.derrick.big_event.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

                Map<String, Object> claims = new HashMap<>();
                claims.put("id", user.getId());
                claims.put("username", username);

                return Result.success(JwtUtil.genToken(claims));
            }
        }

        return Result.error("Invalid username or password");
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo() {
        // Parse token from header
        Map<String, Object> map = ThreadLocalUtil.get();

        // Get username from JWT
        String username = (String) map.get("username");

        return Result.success(userService.findByUserName(username));
    }
}
