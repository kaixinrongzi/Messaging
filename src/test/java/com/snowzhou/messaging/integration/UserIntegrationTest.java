package com.snowzhou.messaging.integration;

import com.snowzhou.messaging.dao.UserDAO;
import com.snowzhou.messaging.dao.UserValidationCodeDAO;
import com.snowzhou.messaging.dto.UserDTO;
import com.snowzhou.messaging.dto.UserValidationCodeDTO;
import com.snowzhou.messaging.enumeration.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc  //
class UserIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserDAO userDAO;
    @Autowired private UserValidationCodeDAO userValidationCodeDAO;

    @BeforeEach
    void deleteOldData(){
        this.userDAO.deleteAll();
        this.userValidationCodeDAO.deleteAll();
    }

    @Test
    void testRegister_differentPasswords_returns400() throws Exception{  // test${Objective}_${scenario}_${expectation}
        var requestBody = """
                {
                    "username": "George",
                    "password": "xcvxvcxxdsfsdfsdf",
                    "repeatPassword": "xcvxvcxxdsfsdf",
                    "nickname": "sssssss",
                    "email": "sdfsdsfdsfdsfsf",
                    "address": "xcvcxvxcvxcxv"
                }
                """;
        this.mockMvc.perform(post("/users/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())     // status code == 400
                .andExpect(content().string("Passwords are not matched"));
    }

    @Test
    void testRegister_tooShortPassword_returns400() throws Exception {
        var requestBody = """
                {
                    "username": "George",
                    "password": "xcvx",
                    "repeatPassword": "xcvx",
                    "nickname": "ssssssss",
                    "gender": "FEMALE",
                    "email": "sdfsdsfdsfdsfsf",
                    "address": "xcvcxvxcvxcxv"
                }
                """;
        this.mockMvc.perform(post("/users/register")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Too short password"));

    }

    @Test
    void testRegister_nickNameIsEmpty_returns400() throws Exception {
        var requestBody = """
                {
                    "username": "George",
                    "password": "xcvx",
                    "repeatPassword": "xcvx",
                    "gender": "FEMALE",
                    "email": "sdfsdsfdsfdsfsf",
                    "address": "xcvcxvxcvxcxv"
                }
                """;
        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())    // status code == 400
                .andExpect(content().string("Username or nickname is empty"));
    }

    @Test
    void testRegister_validInput_returns200() throws Exception {
        // 1. Test if the registration is successful
        var requestBody = """
                {
                    "username": "George123123",
                    "password": "sssssssssssssssss",
                    "repeatPassword": "sssssssssssssssss",
                    "nickname": "ssssssss",
                    "gender": "FEMALE",
                    "email": "sdfsdsfdsfdsfsf@gmail.com",
                    "address": "xcvcxvxcvxcxv"
                }
                """;
        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());   // status code == 200

        // 2. Test if the user info. has been inserted to the database
        UserDTO userDTO = this.userDAO.selectByUsername("George123123");
        assertEquals("sssssssssssssssss", userDTO.getPassword());
        assertEquals("ssssssss", userDTO.getNickname());
        assertEquals(Gender.FEMALE, userDTO.getGender());

        // 3. Test if the user validation code has been inserted the database
        UserValidationCodeDTO userValidationCodeDTO = this.userValidationCodeDAO.selectByUserId(userDTO.getId());
        assertEquals(userDTO.getId(), userValidationCodeDTO.getId());
        assertEquals(6, userValidationCodeDTO.getValidationCode().length());
    }

    @Test
    void testActivate_validInput_returns200() throws Exception {
        // 1. Test if the registration is successful
        var requestBody = """
                {
                    "username": "George123123",
                    "password": "sssssssssssssssss",
                    "repeatPassword": "sssssssssssssssss",
                    "nickname": "ssssssss",
                    "gender": "FEMALE",
                    "email": "sdfsdsfdsfdsfsf@gmail.com",
                    "address": "xcvcxvxcvxcxv"
                }
                """;
        this.mockMvc.perform(post("/users/register")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());   // status code == 200

        // 2. Test if the user info. has been inserted to the database
        UserDTO userDTO = this.userDAO.selectByUsername("George123123");
        UserValidationCodeDTO userValidationCodeDTO = this.userValidationCodeDAO.selectByUserId(userDTO.getId());
        requestBody = String.format("""
                {
                    "username": "George123123",
                    "validationCode": "%s"
                }
                """, userValidationCodeDTO.getValidationCode()
        );
        this.mockMvc.perform(post("/users/activate")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());   // status code == 200

    }


}
