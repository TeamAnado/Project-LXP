package com.lxp.user.presentation.controller.request;

import com.lxp.global.exception.LXPException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserPasswordCheckDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserPasswordCheckRequest(Long id, String password) {

    public UserPasswordCheckRequest {
        checkId();
        checkPassword();
    }

    public UserPasswordCheckDto to() {
        return new UserPasswordCheckDto(this.id, this.password);
    }

    private void checkId() {
        if (this.id == null) {
            throw new LXPException("사용자 ID는 필수입니다.");
        }
    }

    private void checkPassword() {
        if (isBlank(this.password)) {
            throw new InvalidPasswordException("비밀번호는 필수입니다.");
        }
    }
}
