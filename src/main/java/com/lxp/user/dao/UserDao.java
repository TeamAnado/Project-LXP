package com.lxp.user.dao;

import com.lxp.support.QueryUtil;
import com.lxp.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public long save(User user) throws SQLException {
        String sql = QueryUtil.getQuery("user.save");
        int result = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getDateCreated());
            ps.setObject(5, user.getDateModified());

            result = ps.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        throw new SQLException("강좌 생성에 실패하였습니다.");
    }
}
