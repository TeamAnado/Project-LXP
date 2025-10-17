package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserFindPasswordDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserFindPasswordRequest(
    Long id,
    String newPassword
) {

    public UserFindPasswordDto to() {
        checkId();
        checkPassword();
        return new UserFindPasswordDto(this.id, this.newPassword);
    }

    private void checkId() {
        if (this.id == null) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
    }

    private void checkPassword() {
        if (isBlank(this.newPassword)) {
            throw new IllegalArgumentException("새 비밀번호는 필수입니다.");
        }
    }

}
