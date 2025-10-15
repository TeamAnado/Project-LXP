package com.lxp.user;

import com.lxp.base.BaseDateModifiedEntity;

import static com.lxp.user.validator.UserValidator.validateEmail;
import static com.lxp.user.validator.UserValidator.validatePassword;
import static com.lxp.user.validator.UserValidator.validateUsername;

public class User extends BaseDateModifiedEntity {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        validateUsername(name);
        validateEmail(email);
        validatePassword(password);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void recordTime() {
        this.recordCreationTime();
        this.recordDateModified();
    }

    public void update(String name) {
        validateUsername(name);
        this.name = name;
    }

    public void updatePassword(String hashedPassword) {
        this.password = hashedPassword;
    }
}
