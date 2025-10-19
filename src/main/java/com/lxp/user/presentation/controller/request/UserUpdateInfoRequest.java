package com.lxp.user.presentation.controller.request;

import com.lxp.global.exception.LXPException;
import com.lxp.user.exception.InvalidNameException;
import com.lxp.user.service.dto.UserUpdateInfoDto;

import static com.lxp.global.support.StringUtils.isBlank;

public record UserUpdateInfoRequest(
    Long id,
    String name
) {

    public UserUpdateInfoRequest {
        checkId(id);
        checkUsername(name);
    }

    public UserUpdateInfoDto to() {
        return new UserUpdateInfoDto(this.id, this.name);
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new LXPException("사용자 ID는 필수입니다.");
        }
    }

    private void checkUsername(String name) {
        if (isBlank(name)) {
            throw new InvalidNameException();
        }
    }

}
