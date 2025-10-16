package com.lxp.user.service.dto;

public record UserFindPasswordDto(
    Long id,
    String newPassword
) {
}
