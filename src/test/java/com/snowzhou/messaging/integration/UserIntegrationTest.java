package com.snowzhou.messaging.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc  //
class UserIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void testRegister_differentPasswords_returns500(){
        var requestBody = "";
        this.mockMvc.perform(post("/users/register")
                .content(requestBody)
    }
}
