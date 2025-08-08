package com.snowzhou.messaging.dto;

import lombok.Data;

@Data
public class UserValidationCodeDTO {

    private int id;
    private int userId;
    private String validationCode;
}
