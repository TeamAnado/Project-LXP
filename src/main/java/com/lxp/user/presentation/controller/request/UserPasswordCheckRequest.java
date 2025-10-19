package com.lxp.user.presentation.controller.request;

import com.lxp.global.exception.LXPException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserPasswordCheckDto;

import static com.lxp.global.support.StringUtils.isBlank;

public record UserPasswordCheckRequest(Long id, String password) {

    public UserPasswordCheckRequest {
        checkId(id);
        checkPassword(password);
    }

    public UserPasswordCheckDto to() {
        return new UserPasswordCheckDto(this.id, this.password);
    }

    @Override
    public String toString() {
        return "UserPasswordCheckRequest{" +
            "id=" + id +
            '}';
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new LXPException("사용자 ID는 필수입니다.");
        }
    }

    private void checkPassword(String password) {
        if (isBlank(password)) {
            throw new InvalidPasswordException("비밀번호는 필수입니다.");
        }
    }

}
