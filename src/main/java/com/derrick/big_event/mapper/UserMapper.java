package com.derrick.big_event.mapper;

import com.derrick.big_event.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    /**
     * Update user information
     *
     * @param user
     */
    @Update("UPDATE user SET nickname=#{nickname}, email=#{email}, update_time=#{updateTime} WHERE id = #{id}")
    void updateUserInfo(User user);

    /**
     * Update user avatar
     *
     * @param avatarUrl
     */
    @Update("UPDATE user SET user_pic=#{avatarUrl}, update_time = now() WHERE id = #{userId}")
    void updateAvatar(String avatarUrl, Integer userId);

    /**
     * Update user password
     *
     * @param pwd
     */
    @Update("UPDATE user SET password=#{pwd}, update_time = now() WHERE id = #{userId}")
    void updatePwd(String pwd, Integer userId);
}
