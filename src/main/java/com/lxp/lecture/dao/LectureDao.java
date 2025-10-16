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

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setLectureParameters(stmt, lecture);
            return executeAndGetKey(stmt);
        } catch (SQLException e) {
            throw new LectureNotSavedException(e);
        }
    }

    private void setLectureParameters(PreparedStatement stmt, Lecture lecture) throws SQLException {
        stmt.setLong(1, lecture.getSectionId());
        stmt.setString(2, lecture.getTitle());
        stmt.setString(3, lecture.getDescription());
        stmt.setTimestamp(4, Timestamp.valueOf(lecture.getDateCreated()));
        stmt.setTimestamp(5, Timestamp.valueOf(lecture.getDateModified()));
    }

    private long executeAndGetKey(PreparedStatement stmt) throws SQLException {
        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new LectureNotSavedException();
        }
        return getGeneratedKey(stmt);
    }

    private long getGeneratedKey(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new FailedToRetrieveIdException();
            }
        }
    }
}
