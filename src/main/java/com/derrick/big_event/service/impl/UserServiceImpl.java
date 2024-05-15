package com.derrick.big_event.service.impl;

import com.derrick.big_event.mapper.UserMapper;
import com.derrick.big_event.pojo.User;
import com.derrick.big_event.service.UserService;
import com.derrick.big_event.utils.Md5Util;
import com.derrick.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

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

    @Override
    public void updateUserInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUserInfo(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        userMapper.updateAvatar(avatarUrl, userId);
    }

    @Override
    public void updatePwd(String newPwd) {
        // Get user info from ThreadLocal
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        userMapper.updatePwd(Md5Util.getMD5String(newPwd), userId);
    }
}
