package com.lxp.user.service.dto;

public record UserFindPasswordDto(
    Long id,
    String newPassword
) {

    @Override
    public String toString() {
        return "UserFindPasswordDto{id=" + id + ", newPassword='[PROTECTED]'}";
    }

}
