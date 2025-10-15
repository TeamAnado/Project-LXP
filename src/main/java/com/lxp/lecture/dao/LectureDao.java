package com.lxp.lecture.dao;

import com.lxp.exception.LXPException;
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
            lecture.recordTime();

            pstmt.setLong(1, lecture.getSectionId());
            pstmt.setString(2, lecture.getTitle());
            pstmt.setString(3, lecture.getDescription());
            pstmt.setTimestamp(4, Timestamp.valueOf(lecture.getDateCreated()));
            pstmt.setTimestamp(5, Timestamp.valueOf(lecture.getDateModified()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("강의 생성에 실패했습니다. (0 rows affected)");
            }

            return getGeneratedKey(pstmt);
        } catch (SQLException e) {
            throw new LXPException("강의 저장을 실패하였습니다.", e);
        }
    }

    private long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("강의 생성 후 ID를 받아오지 못했습니다.");
            }
        }
    }
}
