package com.derrick.big_event.service;

import com.derrick.big_event.pojo.User;

public interface UserService {
    User findByUserName(String userName);

    void register(String username, String password);
}
