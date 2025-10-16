package com.lxp.user.dto;

import com.lxp.user.User;

public record UserSaveResponse(String email, String name) {

    public static UserSaveResponse to(User user) {
        return new UserSaveResponse(user.getEmail(), user.getName());
    }
}
