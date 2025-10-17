package com.lxp.lecture.dao;

import com.lxp.lecture.exception.FailedToRetrieveIdException;
import com.lxp.lecture.exception.LectureNotSavedException;
import com.lxp.lecture.model.Lecture;
import com.lxp.support.QueryUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class LectureDao {

    private final Connection connection;

    public LectureDao(Connection connection) {
        this.connection = connection;
    }

    public long save(Lecture lecture) {
        String sql = QueryUtil.getQuery("lecture.save");

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setLectureParameters(pstmt, lecture);
            return executeAndGetKey(pstmt);
        } catch (SQLException e) {
            throw new LectureNotSavedException(e);
        }
    }

    private void setLectureParameters(PreparedStatement pstmt, Lecture lecture) throws SQLException {
        pstmt.setString(1, lecture.getTitle());
        pstmt.setString(2, lecture.getDescription());
        pstmt.setTimestamp(3, Timestamp.valueOf(lecture.getDateCreated()));
        pstmt.setTimestamp(4, Timestamp.valueOf(lecture.getDateModified()));
    }

    private long executeAndGetKey(PreparedStatement pstmt) throws SQLException {
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new LectureNotSavedException();
        }
        return getGeneratedKey(pstmt);
    }

    private long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new FailedToRetrieveIdException();
            }
        }
    }

}
