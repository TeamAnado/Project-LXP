package com.lxp.user;

import com.lxp.base.BaseDateModifiedEntity;

import static com.lxp.user.validator.UserValidator.validateEmail;
import static com.lxp.user.validator.UserValidator.validateUsername;

public class User extends BaseDateModifiedEntity {
    private String name;
    private String email;
    private String password;

    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User createWithHashedPassword(String name, String email, String hashedPassword) {
        validateUsername(name);
        validateEmail(email);
        return new User(name, email, hashedPassword);
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
        this.recordDateCreated();
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
