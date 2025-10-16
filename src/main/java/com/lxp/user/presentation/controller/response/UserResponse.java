package com.lxp.user.presentation.controller.response;

public record UserResponse(Long id) {

    public static UserResponse empty() {
        return new UserResponse(null);
    }

}
