package com.lxp.user.presentation.controller.response;

import com.lxp.user.dao.vo.UserInfo;

public record UserFindResponse(
    long id,
    String name,
    String email
) {

    public static UserFindResponse from(UserInfo info) {
        return new UserFindResponse(info.id(), info.name(), info.email());
    }

    @Override
    public String toString() {
        return "이름: " + name + '\n' + "이메일: " + email + '\n';
    }

}
