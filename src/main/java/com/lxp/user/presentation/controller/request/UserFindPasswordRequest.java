package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserFindPasswordDto;

public record UserFindPasswordRequest(
    Long id,
    String newPassword
) {

    public UserFindPasswordDto to() {
        return new UserFindPasswordDto(this.id, this.newPassword);
    }

}
