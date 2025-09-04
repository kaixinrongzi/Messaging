package com.snowzhou.messaging.service;

import com.snowzhou.messaging.dao.UserDAO;
import com.snowzhou.messaging.dao.UserValidationCodeDAO;
import com.snowzhou.messaging.services.EmailService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    // mock
    @Mock private UserDAO mockUserDAO = mock(UserDAO.class);
    @Mock private UserValidationCodeDAO userValidationCodeDAO = mock(UserValidationCodeDAO.class);
    @Mock private EmailService emailService = new EmailService();

    // 上面定义行为，下面测试

}
