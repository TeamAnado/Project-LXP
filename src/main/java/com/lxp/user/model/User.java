package com.lxp.user.model;

import com.lxp.base.BaseDateModifiedEntity;

import java.time.LocalDateTime;

public class User extends BaseDateModifiedEntity {

    private String name;
    private String email;
    private String password;

    public User(Long id, String name, String email, String password, LocalDateTime dateCreated, LocalDateTime dateModified) {
        super(id, dateCreated, dateModified);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
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
        this.recordDateCreated();
        this.recordDateModified();
    }

    public void update(String name) {
        this.name = name;
    }

    public void updatePassword(String hashedPassword) {
        this.password = hashedPassword;
    }

}
