package com.lxp.user.presentation.controller.response;

import com.lxp.user.model.User;

public record UserSaveResponse(String email, String name) {

    public static UserSaveResponse to(User user) {
        return new UserSaveResponse(user.getEmail(), user.getName());
    }

}
