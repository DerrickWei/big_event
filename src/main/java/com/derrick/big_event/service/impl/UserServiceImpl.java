package com.derrick.big_event.service.impl;

import com.derrick.big_event.mapper.UserMapper;
import com.derrick.big_event.pojo.User;
import com.derrick.big_event.service.UserService;
import com.derrick.big_event.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.derrick.big_event.utils.Md5Util.getMD5String;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        // Encrypt password
        String encryptedPwd = Md5Util.getMD5String(password);

        userMapper.saveNewUser(username, encryptedPwd);
    }
}
