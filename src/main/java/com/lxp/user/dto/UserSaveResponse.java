package com.lxp.user.dto;

import com.lxp.user.User;

public record UserSaveResponse(long id, String email, String name) {

    public static UserSaveResponse to(User user) {
        return new UserSaveResponse(user.getId(), user.getEmail(), user.getName());
    }
}
