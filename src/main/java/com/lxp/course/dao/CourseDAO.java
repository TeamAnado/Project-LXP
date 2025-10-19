package com.lxp.course.dao;

import com.lxp.course.exception.CourseNotDeletedException;
import com.lxp.course.exception.CourseNotSavedException;
import com.lxp.course.exception.CourseNotUpdatedException;
import com.lxp.global.config.DBConfig;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.global.exception.LXPDatabaseAccessException;
import com.lxp.support.QueryUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public CourseDao() {
    }

    /**
     * Save course to database
     *
     * @param course
     * @return
     * @throws SQLException
     */
    public long save(Course course) {
        String sql = getSafeQuery("course.save");
        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, course.getInstructorId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getCategory().toString());
            pstmt.setString(4, course.getDescription());
            pstmt.setTimestamp(5, Timestamp.valueOf(course.getDateCreated()));
            pstmt.setTimestamp(6, Timestamp.valueOf(course.getDateModified()));

            int result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
            throw new CourseNotSavedException();
        } catch (SQLException e) {
            throw new CourseNotSavedException(e);
        }
    }

    /**
     * Find all courses from database
     *
     * @return
     * @throws SQLException
     */
    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        String sql = getSafeQuery("course.findAll");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Course course = new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("instructor_id"),
                        Category.valueOf(rs.getString("category")),
                        rs.getTimestamp("date_created").toLocalDateTime(),
                        rs.getTimestamp("date_modified").toLocalDateTime()
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강의 목록 조회 중 데이터베이스 오류 발생", e);
        }
        return courseList;
    }

    /**
     * Find course from database by id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Course findById(Long id) {
        String sql = getSafeQuery("course.findById");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("instructor_id"),
                        Category.valueOf(rs.getString("category")),
                        rs.getTimestamp("date_created").toLocalDateTime(),
                        rs.getTimestamp("date_modified").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강의 조회 중 데이터베이스 오류 발생", e);
        }
        return null;
    }

    /**
     * Find courses from database by title keyword
     *
     * @param keyword
     * @return
     * @throws SQLException
     */
    public List<Course> findByTitleContaining(String keyword) {
        List<Course> courseList = new ArrayList<>();
        String sql = getSafeQuery("course.findByTitleContaining");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("instructor_id"),
                        Category.valueOf(rs.getString("category")),
                        rs.getTimestamp("date_created").toLocalDateTime(),
                        rs.getTimestamp("date_modified").toLocalDateTime()
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("제목으로 강의 검색 중 데이터베이스 오류 발생", e);
        }
        return courseList;
    }

    /**
     * Find courses from database by category
     *
     * @param category
     * @return
     * @throws SQLException
     */
    public List<Course> findByCategory(Category category) {
        List<Course> courseList = new ArrayList<>();
        String sql = getSafeQuery("course.findByCategory");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("instructor_id"),
                        Category.valueOf(rs.getString("category")),
                        rs.getTimestamp("date_created").toLocalDateTime(),
                        rs.getTimestamp("date_modified").toLocalDateTime()
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("카테고리별 강의 조회 중 데이터베이스 오류 발생", e);
        }
        return courseList;
    }

    /**
     * Find courses from database by instructor id
     *
     * @param instructorId
     * @return
     * @throws SQLException
     */
    public List<Course> findByInstructorId(Long instructorId) {
        List<Course> courseList = new ArrayList<>();
        String sql = getSafeQuery("course.findByInstructorId");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, instructorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("instructor_id"),
                        Category.valueOf(rs.getString("category")),
                        rs.getTimestamp("date_created").toLocalDateTime(),
                        rs.getTimestamp("date_modified").toLocalDateTime()
                );
                courseList.add(course);
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강사별 강의 조회 중 데이터베이스 오류 발생", e);
        }
        return courseList;
    }

    /**
     * Update course in database
     *
     * @param course
     * @return
     * @throws SQLException
     */
    public boolean update(Course course) {
        String sql = getSafeQuery("course.update");
        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, course.getInstructorId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getCategory().toString());
            pstmt.setString(4, course.getDescription());
            pstmt.setTimestamp(5, Timestamp.valueOf(course.getDateModified()));
            pstmt.setLong(6, course.getId());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new CourseNotUpdatedException(e);
        }
    }

    /**
     * Delete course from database by id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean delete(Long id) {
        String sql = getSafeQuery("course.delete");
        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new CourseNotDeletedException(e);
        }
    }

    /**
     * Check if course exists in database by id
     *
     * @param courseId
     * @return
     * @throws SQLException
     */
    public boolean existsById(long courseId) {
        String sql = getSafeQuery("course.existsById");
        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강의 존재 확인 중 데이터베이스 오류 발생", e);
        }
        return false;
    }

    /**
     * 안전한 쿼리 조회 - 키가 존재하지 않으면 예외 발생
     *
     * @param key 쿼리 키
     * @return SQL 쿼리 문자열
     * @throws LXPDatabaseAccessException 쿼리 키가 존재하지 않을 때
     */
    private String getSafeQuery(String key) {
        String query = QueryUtil.getQuery(key);
        if (query == null) {
            throw new LXPDatabaseAccessException("SQL Query 키 누락: " + key);
        }
        return query;
    }

}
