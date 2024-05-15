package com.derrick.big_event.service;

import com.derrick.big_event.pojo.User;

import java.util.Map;

public interface UserService {
    User findByUserName(String userName);

    void register(String username, String password);

    void updateUserInfo(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
