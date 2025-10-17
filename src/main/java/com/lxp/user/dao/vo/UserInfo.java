package com.lxp.user.dao.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

public record UserInfo(long id, String email, String name) {

    public static UserInfo from(ResultSet rs) throws SQLException {
        return new UserInfo(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("name")
        );
    }

}
