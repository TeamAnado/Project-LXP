package com.lxp.user.presentation.controller.request;

import com.lxp.user.exception.InvalidEmailException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserLoginDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserLoginRequest(
    String email,
    String password
) {

    public UserLoginRequest {
        checkEmail(email);
        checkPassword(password);
    }

    public UserLoginDto to() {
        return new UserLoginDto(this.email, this.password);
    }

    private void checkEmail(String email) {
        if (isBlank(email)) {
            throw new InvalidEmailException("이메일은 필수입니다.");
        }
    }

    private void checkPassword(String password) {
        if (isBlank(password)) {
            throw new InvalidPasswordException("비밀번호는 필수입니다.");
        }
    }

}
