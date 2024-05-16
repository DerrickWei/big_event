package com.derrick.big_event.controller;

import com.derrick.big_event.pojo.Result;
import com.derrick.big_event.pojo.User;
import com.derrick.big_event.service.UserService;
import com.derrick.big_event.utils.JwtUtil;
import com.derrick.big_event.utils.Md5Util;
import com.derrick.big_event.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

                // Save token to redis with expires in 1 hour
                String token = JwtUtil.genToken(claims);
                ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
                valueOperations.set(token, token, 1, TimeUnit.HOURS);

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

    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody @Validated User user) {
        userService.updateUserInfo(user);

        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);

        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        // Get Pwds from request body
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        // Check if Pwds is not null
        if (
            (!StringUtils.hasLength(oldPwd))
            || (!StringUtils.hasLength(newPwd))
            || (!StringUtils.hasLength(rePwd))
        ) {
            return Result.error("Invalid old password or new password");
        }

        // Check if oldPwd is matched from database
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);

        if (!Md5Util.checkPassword(oldPwd, loginUser.getPassword())) {
            return Result.error("Invalid old password");
        }

        // Check if newPwd is same with rePwd
        if (!rePwd.equals(newPwd)) {
            return Result.error("Passwords do not match");
        }

        userService.updatePwd(newPwd);

        // Remove redisToken
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.getOperations().delete(token);

        return Result.success();
    }
}
