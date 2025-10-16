package com.lxp.course.dao;

import com.lxp.config.DBConfig;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.support.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private final Connection connection;

    public CourseDAO() throws SQLException {
        this.connection = DBConfig.getInstance().getConnection();
    }

    /**
     * Find every course from database
     * @return List of course object
     * @throws SQLException
     */
    public List<Course> findAll() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = QueryUtil.getQuery("course.findAll");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet re = pstmt.executeQuery();
            while (re.next()) {
                Course course = new Course(
                        re.getLong("id"),
                        re.getString("title"),
                        re.getString("description"),
                        re.getLong("instructor_id"),
                        Category.valueOf(re.getString("category")),
                        re.getTimestamp("date_created").toLocalDateTime(),
                        re.getTimestamp("date_modified").toLocalDateTime()
                );
                courseList.add(course);
            }
        }
        return courseList;
    }

    /**
     * Save course object
     * @param course
     * @return
     * @throws SQLException
     */
    public long Save(Course course) throws SQLException {
        String sql = QueryUtil.getQuery("course.save");
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, course.getInstructorId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getCategory().toString());
            pstmt.setString(4, course.getDescription());
            pstmt.setString(5, course.getDateCreated().toString());
            pstmt.setString(6, course.getDateModified().toString());
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
     * Find course from database by id.
     * @param id
     * @return
     * @throws SQLException
     */
    public Course findById(Long id) throws SQLException {
        String sql = QueryUtil.getQuery("course.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
     * Find course from database by string literal on title.
     * @param Keyword
     * @return
     * @throws SQLException
     */
    public List<Course> findByTitleContaining(String Keyword) throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = QueryUtil.getQuery("course.findByTitleContaining");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + Keyword + "%");
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
}
