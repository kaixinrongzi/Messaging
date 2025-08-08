package com.snowzhou.messaging.services;

import com.snowzhou.messaging.dao.UserDAO;
import com.snowzhou.messaging.dao.UserValidationCodeDAO;
import com.snowzhou.messaging.dto.UserDTO;
import com.snowzhou.messaging.dto.UserValidationCodeDTO;
import com.snowzhou.messaging.enumeration.Gender;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserService {

    // dependency injection
    @Autowired private UserDAO userDAO;
    @Autowired private UserValidationCodeDAO userValidationCodeDAO;
    @Autowired private EmailService emailService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", // regular expression
            Pattern.CASE_INSENSITIVE
    );

    public void register(String username,
                         String password,
                         String repeatPassword,
                         String nickname,
                         String address,
                         String email,
                         Gender gender) throws Exception {
        // validation
        if (!password.equals(repeatPassword)) {
            throw new Exception();
        }

        if (password.length() < 8) {
            throw new Exception();
        }

        if (username == null || username.isEmpty() || nickname == null || nickname.isEmpty()) {
            throw new Exception();
        }

        if (!isValidEmail(email)) {
            throw new Exception();
        }

        // validated => generate an object for this user
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setNickname(nickname);
        userDTO.setEmail(email);
        userDTO.setAddress(address);
        userDTO.setGender(gender);
        userDTO.setRegisterTime(new Date());
        userDTO.setIsValid(false);

        // insert the user into database
        this.userDAO.insert(userDTO);

        // generate validation code for this user
        UserDTO selectedUserDTO = this.userDAO.selectByUsername(username);

        String validationCode = RandomStringUtils.secure().nextNumeric(6);
        UserValidationCodeDTO userValidationCodeDTO = new UserValidationCodeDTO();
        userValidationCodeDTO.setUserId(selectedUserDTO.getId());
        userValidationCodeDTO.setValidationCode(validationCode);

        // insert the validation code for.this user
        this.userValidationCodeDAO.insert(userValidationCodeDTO);

        // send an email to this user
        this.emailService.sendEmail(email, "Validation Code", validationCode);




    }

    public String login(String username, String password) throws Exception{
        // get the user object from database
        UserDTO selectedUserDTO = this.userDAO.selectByUsername(username);
        // check if the user object exists in the database or if the user is valid
        if (selectedUserDTO == null || !selectedUserDTO.getIsValid()){
            throw new Exception();
        }
        // check if user provides a correct pwd
        if (!selectedUserDTO.getPassword().equals(password)) {
            throw new Exception();
        }

        //generate a login token and put it for this user in database
        String loginToken = RandomStringUtils.secure().nextAlphanumeric(64);
        this.userDAO.login(selectedUserDTO.getId(), loginToken, new Date());

        return loginToken;

    }

    public void activate(String username, String validationCode) throws Exception {

        UserDTO selectedUserDTO = this.userDAO.selectByUsername(username);

        // check if the user object exists in the database
        if (selectedUserDTO == null){
            throw new Exception();
        }

        // check if the user has been activated (should not be activated)
        if (selectedUserDTO.getIsValid()) {
            throw new Exception();
        }

        // check if the user exists in the validation_code database
        var userValidationCodeDTO = this.userValidationCodeDAO.selectByUserId(selectedUserDTO.getId());
        if (userValidationCodeDTO == null) {
            throw new Exception();
        }

        // check if the user provides a matching validation code
        if (!userValidationCodeDTO.getValidationCode().equals(validationCode)) {
            throw new Exception();
        }

        // update user to valid
        this.userDAO.updateToValid(selectedUserDTO.getId());

        // delete the user from the validation_code database
        this.userValidationCodeDAO.delete(userValidationCodeDTO.getId());

    }

    public UserDTO authenticate(String loginToken) throws Exception {
        UserDTO selectedUserDTO = this.userDAO.selectByLoginToken(loginToken);
        if (selectedUserDTO == null) {
            throw new Exception();
        }
        if (new Date().getTime() - selectedUserDTO.getLastLoginTime().getTime() > Duration.ofDays(14).toMillis()) {
            throw new Exception();
        }

        return selectedUserDTO;

    }

    public boolean isValidEmail(String email) throws Exception {
        if (email == null){
            throw new Exception();
        }

        return EMAIL_PATTERN.matcher(email).matches();
    }

}
