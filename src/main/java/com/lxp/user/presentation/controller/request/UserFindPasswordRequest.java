package com.lxp.user.presentation.controller.request;

import com.lxp.global.exception.LXPException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserFindPasswordDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserFindPasswordRequest(
    Long id,
    String newPassword
) {

    public UserFindPasswordRequest {
        checkId(id);
        checkPassword(newPassword);
    }

    public UserFindPasswordDto to() {
        return new UserFindPasswordDto(this.id, this.newPassword);
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new LXPException("사용자 ID는 필수입니다.");
        }
    }

    private void checkPassword(String newPassword) {
        if (isBlank(newPassword)) {
            throw new InvalidPasswordException("새 비밀번호는 필수입니다.");
        }
    }

}
