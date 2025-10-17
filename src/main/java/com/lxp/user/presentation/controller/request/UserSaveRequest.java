package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserSaveDto;

public record UserSaveRequest(
    String name,
    String email,
    String rawPassword
) {

    public UserSaveDto to() {
        return new UserSaveDto(this.name, this.email, this.rawPassword);
    }

}
