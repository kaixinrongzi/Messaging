package com.snowzhou.messaging.models;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String memo;
    private String location;
    private Date createTime;
}
