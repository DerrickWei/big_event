package com.derrick.big_event.mapper;

import com.derrick.big_event.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * Find user by username
     *
     * @param username
     * @return
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUserName(String username);

    /**
     * Create new user
     *
     * @param username
     * @param password
     */
    @Insert("INSERT INTO user(username, password, create_time, update_time)" +
            " VALUES (#{username}, #{encryptedPwd}, now(), now())")
    void saveNewUser(String username, String encryptedPwd);
}
