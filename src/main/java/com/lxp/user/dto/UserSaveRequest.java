package com.lxp.user.dto;

import com.lxp.user.User;

public record UserSaveRequest(String name, String email, String rawPassword) {
    public User to(String hashedPassword) {
        return new User(name, email, hashedPassword);
    }
}
