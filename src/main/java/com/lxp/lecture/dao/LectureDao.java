package com.lxp.lecture.dao;

import com.lxp.global.exception.LXPDatabaseAccessException;
import com.lxp.global.support.QueryUtil;
import com.lxp.lecture.exception.LectureNotSavedException;
import com.lxp.lecture.exception.LectureNotUpdatedException;
import com.lxp.lecture.model.Lecture;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LectureDao {

    private final Connection connection;

    public LectureDao(Connection connection) {
        this.connection = connection;
    }

    public long save(Lecture lecture) {
        String sql = getSafeQuery("lecture.save");
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setLectureParameters(pstmt, lecture);
            return executeAndGetKey(pstmt);
        } catch (SQLException e) {
            throw new LectureNotSavedException(e);
        }
    }

    public Optional<Lecture> findById(long lectureId) {
        String sql = getSafeQuery("lecture.findById");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lectureId);
            try (ResultSet rs = ps.executeQuery()) {
                return processFindByIdResultSet(rs);
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강의 조회 중 오류가 발생했습니다.", e);
        }
    }

    public List<Lecture> findLecturesByCourseId(long courseId) {
        String sql = getSafeQuery("lecture.findLecturesByCourseId");
        List<Lecture> lectures = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lectures.add(mapToLecture(rs));
                }
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강의 목록 조회 중 오류가 발생했습니다.", e);
        }
        return lectures;
    }

    public void update(Lecture lecture) {
        String sql = getSafeQuery("lecture.update");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setUpdateParameters(ps, lecture);
            executeUpdateAndCheck(ps);
        } catch (SQLException e) {
            throw new LectureNotUpdatedException(e);
        }
    }

    public void delete(long lectureId) {
        String sql = getSafeQuery("lecture.delete");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, lectureId);
            executeUpdateAndCheck(ps);
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강의 삭제 중 오류가 발생했습니다.", e);
        }
    }

    private void executeUpdateAndCheck(PreparedStatement ps) throws SQLException {
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new LXPDatabaseAccessException("강의 정보가 수정되지 않았습니다.");
        }
    }

    private Optional<Lecture> processFindByIdResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return Optional.of(mapToLecture(rs));
        }
        return Optional.empty();
    }

    private void setLectureParameters(PreparedStatement pstmt, Lecture lecture) throws SQLException {
        pstmt.setLong(1, lecture.getCourseId());
        pstmt.setString(2, lecture.getTitle());
        pstmt.setString(3, lecture.getDescription());
        pstmt.setString(4, lecture.getPath());
        pstmt.setTimestamp(5, Timestamp.valueOf(lecture.getDateCreated()));
        pstmt.setTimestamp(6, Timestamp.valueOf(lecture.getDateModified()));
    }

    private long executeAndGetKey(PreparedStatement pstmt) throws SQLException {
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new LXPDatabaseAccessException("강의가 저장되지 않았습니다.");
        }
        return getGeneratedKey(pstmt);
    }

    private long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new LXPDatabaseAccessException("저장된 강의의 ID를 가져오는데 실패했습니다.");
            }
        }
    }

    private Lecture mapToLecture(ResultSet rs) throws SQLException {
        return new Lecture(
                rs.getLong("id"),
                rs.getLong("course_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("path"),
                rs.getTimestamp("date_created").toLocalDateTime(),
                rs.getTimestamp("date_modified").toLocalDateTime()
        );
    }

    private void setUpdateParameters(PreparedStatement ps, Lecture lecture) throws SQLException {
        ps.setLong(1, lecture.getCourseId());
        ps.setString(2, lecture.getTitle());
        ps.setString(3, lecture.getDescription());
        ps.setString(4, lecture.getPath());
        ps.setTimestamp(5, Timestamp.valueOf(lecture.getDateModified()));
        ps.setLong(6, lecture.getId());
    }

    private String getSafeQuery(String key) {
        String query = QueryUtil.getQuery(key);
        if (query == null) {
            throw new LXPDatabaseAccessException("SQL Query 키 누락: " + key);
        }
        return query;
    }
    
}
