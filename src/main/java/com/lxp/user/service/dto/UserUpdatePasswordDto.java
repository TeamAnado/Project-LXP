package com.lxp.user.service.dto;

public record UserUpdatePasswordDto(
    Long id,
    String oldPassword,
    String newPassword
) {
}
