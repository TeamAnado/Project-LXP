package com.lxp.user.service.dto;

import com.lxp.user.model.User;

public record UserSaveDto(
    String name,
    String email,
    String rawPassword
) {

    public User to(String hashedPassword) {
        User user = new User(name, email, hashedPassword);
        user.recordTime();
        return user;
    }

}
