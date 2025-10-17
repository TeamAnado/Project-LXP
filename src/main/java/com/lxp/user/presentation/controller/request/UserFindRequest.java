package com.lxp.user.presentation.controller.request;


import com.lxp.user.service.dto.UserFindDto;

public record UserFindRequest(
    long id
) {

    public UserFindDto to() {
        return new UserFindDto(this.id);
    }

}
