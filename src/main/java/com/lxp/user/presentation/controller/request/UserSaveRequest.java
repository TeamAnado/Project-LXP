package com.lxp.user.presentation.controller.request;

import com.lxp.user.exception.InvalidEmailException;
import com.lxp.user.exception.InvalidNameException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserSaveDto;

import static com.lxp.global.support.StringUtils.isBlank;

public record UserSaveRequest(
    String name,
    String email,
    String rawPassword
) {

    public UserSaveRequest {
        checkName(name);
        checkEmail(email);
        checkPassword(rawPassword);
    }

    public UserSaveDto to() {
        return new UserSaveDto(this.name, this.email, this.rawPassword);
    }

    @Override
    public String toString() {
        return "UserSaveRequest{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

    private void checkName(String name) {
        if (isBlank(name)) {
            throw new InvalidNameException();
        }
    }

    private void checkEmail(String email) {
        if (isBlank(email)) {
            throw new InvalidEmailException("이메일은 필수입니다.");
        }
    }

    private void checkPassword(String rawPassword) {
        if (isBlank(rawPassword)) {
            throw new InvalidPasswordException("새 비밀번호는 필수입니다.");
        }
    }

}
