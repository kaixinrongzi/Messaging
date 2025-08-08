package com.snowzhou.messaging.requests;

import lombok.Data;

@Data
public class ActivateUserRequest {
    private String username;
    private String password;
    private String validationCode;

}
