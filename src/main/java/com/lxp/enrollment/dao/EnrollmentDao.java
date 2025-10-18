package com.lxp.enrollment.dao;

import com.lxp.enrollment.exception.EnrollmentDeleteException;
import com.lxp.enrollment.exception.EnrollmentSaveException;
import com.lxp.enrollment.exception.FindLectureUserException;
import com.lxp.enrollment.exception.FindUserException;
import com.lxp.enrollment.model.State;
import com.lxp.exception.LXPException;
import com.lxp.support.QueryUtil;

import java.sql.*;

public class EnrollmentDao {

    private final Connection connection;

    public EnrollmentDao(Connection connection) {
        this.connection = connection;
    }

    public boolean findByUser(long userId, long courseId) {

        String sql = QueryUtil.getQuery("enrollment.findUser");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, courseId);

            try (ResultSet resultset = pstmt.executeQuery()) {
                return (resultset.next());
            }
        } catch (SQLException e) {
            throw new FindUserException();
        }

    }

    public boolean save(long userId, long courseId) {

        String sql = QueryUtil.getQuery("enrollment.save");

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, courseId);
            pstmt.setInt(3, State.IN_PROGRESS.getCode());

            int result = pstmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            throw new EnrollmentSaveException();
        }
    }

    public boolean delete(long userId, long courseId) {

        String sql = QueryUtil.getQuery("enrollment.delete");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, State.CANCELLED.getCode());
            pstmt.setLong(2, userId);
            pstmt.setLong(3, courseId);
            pstmt.setInt(4, State.IN_PROGRESS.getCode());

            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            throw new EnrollmentDeleteException();
        }
    }

    public boolean complete(long userId, long courseId) {

        String sql = QueryUtil.getQuery("enrollment.complete");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, State.COMPLETED.getCode());
            pstmt.setLong(2, userId);
            pstmt.setLong(3, courseId);
            pstmt.setInt(4, State.IN_PROGRESS.getCode());

            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            throw new LXPException("수강 완료중 오류가 발생하였습니다.", e);
        }
    }

    public boolean isUserEnrolled(long userId, long lectureId) {
        // TODO : lecture id 를 기반으로 해당 lecture가 포함된 course 추적
        // TODO : user id 를 기반으로 해당 user가 course를 수강하고 있는지 확인
        // TODO: 결과값 반환

        String sql = QueryUtil.getQuery("enrollment.isUserEnrolled");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, lectureId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new FindLectureUserException();
        }
    }
}
