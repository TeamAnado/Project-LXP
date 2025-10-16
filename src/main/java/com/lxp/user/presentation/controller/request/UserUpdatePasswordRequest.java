package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserUpdatePasswordDto;

public record UserUpdatePasswordRequest(
    Long id,
    String oldPassword,
    String newPassword
) {

    public UserUpdatePasswordDto to() {
        return new UserUpdatePasswordDto(id, this.oldPassword, this.newPassword);
    }

}
