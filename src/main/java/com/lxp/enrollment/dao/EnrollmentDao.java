package com.lxp.enrollment.dao;

import com.lxp.enrollment.exception.AlreadyEnrolledException;
import com.lxp.enrollment.exception.EnrollmentCompleteException;
import com.lxp.enrollment.exception.EnrollmentDeleteException;
import com.lxp.enrollment.exception.EnrollmentSaveException;
import com.lxp.enrollment.exception.FindLectureUserException;
import com.lxp.enrollment.exception.FindUserException;
import com.lxp.enrollment.model.State;
import com.lxp.enrollment.service.dto.EnrollmentCourseDto;
import com.lxp.global.config.DBConfig;
import com.lxp.support.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDao {

    public EnrollmentDao() {

    }

    public boolean findByUser(long userId, long courseId) {
        String sql = QueryUtil.getQuery("enrollment.findByUser");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, courseId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return (rs.next());
            }
        } catch (SQLException e) {
            throw new FindUserException(e);
        }

    }

    public boolean save(long userId, long courseId) {

        final String sql = QueryUtil.getQuery("enrollment.save");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, courseId);
            pstmt.setInt(3, State.IN_PROGRESS.getCode());
            return pstmt.executeUpdate() == 1;

        } catch (SQLIntegrityConstraintViolationException duplicate) {
            throw new AlreadyEnrolledException(duplicate);
        } catch (SQLException e) {
            throw new EnrollmentSaveException(e);
        }
    }

    public boolean delete(long userId, long courseId) {

        String sql = QueryUtil.getQuery("enrollment.delete");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, State.CANCELLED.getCode());
            pstmt.setLong(2, userId);
            pstmt.setLong(3, courseId);
            pstmt.setInt(4, State.IN_PROGRESS.getCode());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            throw new EnrollmentDeleteException(e);
        }
    }

    public boolean complete(long userId, long courseId) {

        String sql = QueryUtil.getQuery("enrollment.complete");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, State.COMPLETED.getCode());
            pstmt.setLong(2, userId);
            pstmt.setLong(3, courseId);
            pstmt.setInt(4, State.IN_PROGRESS.getCode());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            throw new EnrollmentCompleteException(e);
        }
    }

    public List<EnrollmentCourseDto> findCoursesByUser(long userId) {
        String sql = QueryUtil.getQuery("enrollment.findCoursesByUser");
        List<EnrollmentCourseDto> courses = new ArrayList<>();

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(new EnrollmentCourseDto(
                            rs.getLong("course_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("state")
                    ));
                }
                return courses;
            }
        } catch (SQLException e) {
            throw new FindUserException(e);
        }
    }

    public boolean isUserEnrolled(long userId, long lectureId) {

        String sql = QueryUtil.getQuery("enrollment.isUserEnrolled");

        try (Connection connection = DBConfig.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, lectureId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new FindLectureUserException(e);
        }
    }


}
