package com.lxp.user.dao;

import com.lxp.exception.LXPDatabaseAccessException;
import com.lxp.support.QueryUtil;
import com.lxp.user.dao.vo.UserAuthInfo;
import com.lxp.user.exception.UserNotSavedException;
import com.lxp.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<UserAuthInfo> findById(long id) throws LXPDatabaseAccessException {
        String sql = QueryUtil.getQuery("user.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return getUserAuthInfo(pstmt);
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("아이디 조회 중 데이터베이스 접속 중 오류 발생", e);
        }
    }

    public void save(User user) {
        String sql = QueryUtil.getQuery("user.save");

        setDateTimeIfNull(user);
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setTimestamp(4, Timestamp.valueOf(user.getDateCreated()));
            pstmt.setTimestamp(5, Timestamp.valueOf(user.getDateModified()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new UserNotSavedException(e);
        }
    }

    public boolean existByEmail(String email) {
        String sql = QueryUtil.getQuery("user.existByEmail");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            return existParams(pstmt);
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("이메일 체크 중 데이터베이스 접속 중 오류 발생", e);
        }
    }

    public Optional<UserAuthInfo> findByEmail(String email) {
        String sql = QueryUtil.getQuery("user.findByEmail");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            return getUserAuthInfo(pstmt);
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("이메일 조회 중 데이터베이스 접속 중 오류 발생", e);
        }
    }

    public boolean existById(long id) {
        String sql = QueryUtil.getQuery("user.existById");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return existParams(pstmt);
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("아이디 체크 중 데이터베이스 접속 중 오류 발생", e);
        }
    }

    public boolean updatePassword(long id, LocalDateTime dateModified, String hashedPassword) {
        String sql = QueryUtil.getQuery("user.updatePassword");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setTimestamp(2, Timestamp.valueOf(dateModified));
            pstmt.setLong(3, id);

            return isUpdated(pstmt);
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("비밀번호 업데이트 중 데이터베이스 오류 발생", e);
        }
    }

    private boolean existParams(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private Optional<UserAuthInfo> getUserAuthInfo(PreparedStatement pstmt) throws SQLException {
        Optional<UserAuthInfo> result = Optional.empty();
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                result = Optional.of(UserAuthInfo.from(rs));
            }
            return result;
        }
    }

    private boolean isUpdated(PreparedStatement pstmt) throws SQLException {
        return pstmt.executeUpdate() > 0;
    }

    private void setDateTimeIfNull(User user) {
        if (user.getDateCreated() == null || user.getDateModified() == null) {
            user.recordTime();
        }
    }

}
