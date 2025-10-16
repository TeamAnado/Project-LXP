package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserLoginDto;

public record UserLoginRequest(
    String email,
    String password
) {

    public UserLoginDto to() {
        return new UserLoginDto(this.email(), this.password());
    }

}
