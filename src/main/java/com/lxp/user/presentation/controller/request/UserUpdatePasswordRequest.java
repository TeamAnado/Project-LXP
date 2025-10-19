package com.lxp.user.presentation.controller.request;

import com.lxp.global.exception.LXPException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.service.dto.UserUpdatePasswordDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserUpdatePasswordRequest(
    Long id,
    String oldPassword,
    String newPassword
) {

    public UserUpdatePasswordRequest {
        checkId(id);
        checkPassword(oldPassword, newPassword);
    }

    public UserUpdatePasswordDto to() {
        return new UserUpdatePasswordDto(this.id, this.oldPassword, this.newPassword);
    }

    @Override
    public String toString() {
        return "UserUpdatePasswordRequest{id=" + id + '}';
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new LXPException("사용자 ID는 필수입니다.");
        }
    }

    private void checkPassword(String oldPassword, String newPassword) {
        if (isBlank(newPassword) || isBlank(oldPassword)) {
            throw new InvalidPasswordException("이전 비밀번호와 새 비밀번호는 필수입니다.");
        }
    }

}
