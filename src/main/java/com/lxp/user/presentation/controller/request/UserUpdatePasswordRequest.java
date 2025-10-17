package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserUpdatePasswordDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserUpdatePasswordRequest(
    Long id,
    String oldPassword,
    String newPassword
) {

    public UserUpdatePasswordDto to() {
        checkId();
        checkPassword();
        return new UserUpdatePasswordDto(id, this.oldPassword, this.newPassword);
    }

    private void checkId() {
        if (this.id == null) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
    }

    private void checkPassword() {
        if (isBlank(this.newPassword) || isBlank(this.oldPassword)) {
            throw new IllegalArgumentException("이전 비밀번호는 필수입니다.");
        }
    }
}
