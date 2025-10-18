package com.lxp.user.presentation.controller.request;

import com.lxp.global.exception.LXPException;
import com.lxp.user.service.dto.UserFindDto;

public record UserFindRequest(
    Long id
) {

    public UserFindRequest {
        if (id == null) {
            throw new LXPException("사용자 ID는 필수입니다.");
        }
    }

    public UserFindDto to() {
        return new UserFindDto(this.id);
    }

}
