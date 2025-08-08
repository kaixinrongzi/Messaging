package com.snowzhou.messaging.dao;

import com.snowzhou.messaging.dto.UserValidationCodeDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserValidationCodeDAO {

    @Insert("INSERT INTO user_validation_code (user_id, validation_code) VALUES (#{userId}, #{validationCode})")
    void insert(UserValidationCodeDTO userValidationCodeDTO);

    @Select("SELECT * FROM user_validation_code WHERE user_id = #{userId}")
    UserValidationCodeDTO selectByUserId(int userId);

    @Delete("DELETE FROM user_validation_code WHERE id = #{userId}")
    void delete(int userId);
}
