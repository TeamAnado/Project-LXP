package com.lxp.user.dao;

import com.lxp.exception.LXPDatabaseAccessException;
import com.lxp.support.QueryUtil;
import com.lxp.user.User;
import com.lxp.user.exception.UserNotSavedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void save(User user) {
        String sql = QueryUtil.getQuery("user.save");

        user.recordTime();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setObject(4, user.getDateCreated());
            ps.setObject(5, user.getDateModified());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UserNotSavedException(e);
        }
    }

    public boolean existByEmail(String email) {
        String sql = QueryUtil.getQuery("user.existByEmail");

        boolean flag = false;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    flag = rs.getInt(1) > 0;
                }
                return flag;
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("이메일 체크 중 데이터베이스 접속 중 오류 발생", e);
        }
    }
}
