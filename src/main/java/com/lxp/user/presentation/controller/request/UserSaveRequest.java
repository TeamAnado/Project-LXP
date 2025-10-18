package com.lxp.user.presentation.controller.request;

import com.lxp.user.exception.InvalidEmailException;
import com.lxp.user.exception.InvalidNameException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserSaveDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserSaveRequest(
    String name,
    String email,
    String rawPassword
) {

    public UserSaveRequest{
        checkName();
        checkEmail();
        checkPassword();
    }

    public UserSaveDto to() {
        return new UserSaveDto(this.name, this.email, this.rawPassword);
    }

    private void checkName() {
        if (isBlank(this.name)) {
            throw new InvalidNameException();
        }
    }

    private void checkEmail() {
        if (isBlank(this.email)) {
            throw new InvalidEmailException("이메일은 필수입니다.");
        }
    }

    private void checkPassword() {
        if (isBlank(this.rawPassword)) {
            throw new InvalidPasswordException("새 비밀번호는 필수입니다.");
        }
    }

}
