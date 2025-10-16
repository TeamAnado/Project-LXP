package com.lxp.user.dao.vo;

import com.lxp.user.presentation.controller.response.UserResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public record UserAuthInfo(Long id, String email, String password) {

    public static UserAuthInfo from(ResultSet rs) throws SQLException {
        return new UserAuthInfo(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password")
        );
    }

    public static UserAuthInfo empty() {
        return new UserAuthInfo(null, "", "");
    }

    public UserResponse toResponse() {
        return new UserResponse(this.id);
    }

}
