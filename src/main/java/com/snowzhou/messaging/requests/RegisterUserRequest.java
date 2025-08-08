package com.snowzhou.messaging.requests;

import com.snowzhou.messaging.enumeration.Gender;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
    private String repeatPassword;
    private String nickname;
    private String address;
    private String email;
    private Gender gender;
}
