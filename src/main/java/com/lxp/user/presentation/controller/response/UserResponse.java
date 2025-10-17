package com.lxp.user.presentation.controller.response;

import com.lxp.user.dao.vo.UserAuthInfo;

public record UserResponse(Long id) {

    public static UserResponse empty() {
        return new UserResponse(null);
    }

    public static UserResponse from(UserAuthInfo info) {
        return new UserResponse(info.id());
    }

}
