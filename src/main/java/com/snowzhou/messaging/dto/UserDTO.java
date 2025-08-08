package com.snowzhou.messaging.dto;


import com.snowzhou.messaging.enumeration.Gender;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {

    private int id;   // primary key
    private String username;
    private String nickname;
    private String password;
    private Gender gender;
    private Date registerTime;
    private String email;
    private String address;
    private Boolean isValid;
    private Date lastLoginTime;

}