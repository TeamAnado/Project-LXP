package com.lxp.user.presentation.controller.request;

import com.lxp.user.service.dto.UserUpdateInfoDto;

import static com.lxp.support.StringUtils.isBlank;

public record UserUpdateInfoRequest(
    Long id,
    String name
) {

    public UserUpdateInfoDto to() {
        checkId();
        checkUsername();
        return new UserUpdateInfoDto(this.id, this.name);
    }

    private void checkId() {
        if (this.id == null) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
    }

    private void checkUsername() {
        if (isBlank(this.name)) {
            throw new IllegalArgumentException("사용자 이름은 필수입니다.");
        }
    }

}
