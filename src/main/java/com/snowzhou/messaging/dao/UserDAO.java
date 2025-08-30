package com.snowzhou.messaging.dao;

import java.util.Date;

import com.snowzhou.messaging.dto.UserDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

// data access object
@Mapper
@Repository
public interface UserDAO {

    @Insert("INSERT INTO user (username, nickname, password, email, address, gender, register_time, is_valid)" +
            "VALUES (#{username}, #{nickname}, #{password}, #{email}, #{address}, #{gender}, #{registerTime}, #{isValid})")
    void insert(UserDTO userDTO);   // implementation as been done by MyBatis framework

    @Select("SELECT * FROM user WHERE username = ${username}")
    UserDTO selectByUsername(String username);

    @Update("UPDATE user SET is_valid = 1 WHERE id = ${userId}")
    void updateToValid(int userId);

    @Update("UPDATE user SET login_token = ${loginToken}, last_login_time = #{lastLoginTime})")
    void login(int userId, String loginToken, Date lastLoginTime);

    @Select("SELECT * FROM user WHERE login_token = #{loginToken}")
    UserDTO selectByLoginToken(String loginToken);

    @Delete("DELETE FROM user")
    void deleteAll();




}

