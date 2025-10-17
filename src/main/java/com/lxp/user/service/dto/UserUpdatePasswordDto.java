package com.lxp.user.service.dto;

public record UserUpdatePasswordDto(
    long id,
    String oldPassword,
    String newPassword
) {

    /**
     * 비밀번호 노출 방지
     *
     * @return 비밀번호를 제거하여 반환합니다.
     */
    @Override
    public String toString() {
        return "UserUpdatePasswordDto{id=" + id + ", oldPassword='[PROTECTED]', newPassword='[PROTECTED]'}";
    }

}
