package com.snowzhou.messaging.controllers;

import com.snowzhou.messaging.requests.ActivateUserRequest;
import com.snowzhou.messaging.requests.LoginUserRequest;
import com.snowzhou.messaging.requests.RegisterUserRequest;
import com.snowzhou.messaging.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // register
    @PostMapping("/register")
    public void register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        this.userService.register(registerUserRequest.getUsername(),
                                  registerUserRequest.getPassword(),
                                  registerUserRequest.getRepeatPassword(),
                                  registerUserRequest.getNickname(),
                                  registerUserRequest.getAddress(),
                                  registerUserRequest.getEmail(),
                                  registerUserRequest.getGender());
    }

    // activate
    public void activate(@RequestBody ActivateUserRequest activateUserRequest) throws Exception {
        this.userService.activate(activateUserRequest.getUsername(), activateUserRequest.getValidationCode());
    }

    // login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginUserRequest loginUserRequest) throws Exception {
        String loginToken = this.userService.login(loginUserRequest.getUsername(), loginUserRequest.getPassword());
        HttpCookie httpcookie = ResponseCookie.from("login_token", loginToken)
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();
        ResponseEntity responseEntity = ResponseEntity.ok()
                .header(SET_COOKIE, httpcookie.toString())
                .build();
        return responseEntity;
    }




}
