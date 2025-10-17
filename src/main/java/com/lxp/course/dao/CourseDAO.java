package com.lxp.course.dao;

import com.lxp.global.config.DBConfig;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.support.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public CourseDAO() {
    }

    /**
     * Save course to database
     *
     * @param course
     * @return
     * @throws SQLException
     */
    public long save(Course course) throws SQLException {
        String sql = QueryUtil.getQuery("course.save");
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
        }
        throw new SQLException("Failed to save course on database.");
    }

    /**
     * Find all courses from database
     *
     * @return
     * @throws SQLException
     */
    public List<Course> findAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = QueryUtil.getQuery("course.findAll");

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
    public Course findById(Long id) throws SQLException {
        String sql = QueryUtil.getQuery("course.findById");

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
    public List<Course> findByTitleContaining(String keyword) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = QueryUtil.getQuery("course.findByTitleContaining");

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
    public List<Course> findByCategory(Category category) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        // TODO
        return courseList;
    }

    /**
     * Find courses from database by instructor id
     *
     * @param instructorId
     * @return
     * @throws SQLException
     */
    public List<Course> findByInstructorId(Long instructorId) throws SQLException {
        // TODO
        return null;
    }

    /**
     * Update course in database
     *
     * @param course
     * @return
     * @throws SQLException
     */
    public boolean update(Course course) throws SQLException {
        String sql = QueryUtil.getQuery("course.update");
        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, course.getInstructorId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getCategory().toString());
            pstmt.setString(4, course.getDescription());
            pstmt.setLong(5, course.getId());

            int result = pstmt.executeUpdate();
            return result > 0;
        }
    }

    /**
     * Delete course from database by id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean delete(Long id) throws SQLException {
        String sql = QueryUtil.getQuery("course.delete");
        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);

            int result = pstmt.executeUpdate();
            return result > 0;
        }
    }

    /**
     * Check if course exists in database by id
     *
     * @param courseId
     * @return
     * @throws SQLException
     */
    public boolean existsById(long courseId) throws SQLException {
        String sql = QueryUtil.getQuery("course.existsById");
        try (PreparedStatement pstmt = DBConfig.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

}
