package com.lxp.user.service.dto;

public record UserLoginDto(
    String email,
    String password
) {

    @Override
    public String toString() {
        return "UserLoginDto{email='" + email + "', password='[PROTECTED]'}";
    }

}
