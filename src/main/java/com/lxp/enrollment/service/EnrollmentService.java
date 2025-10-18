package com.lxp.enrollment.service;

import com.lxp.enrollment.dao.EnrollmentDao;
import com.lxp.enrollment.exception.*;
import com.lxp.exception.LXPException;

import java.sql.Connection;

public class EnrollmentService {

    private final Connection connection;
    private final EnrollmentDao enrollmentDao;

    public EnrollmentService(Connection connection, EnrollmentDao enrollmentDao) {

        this.connection = connection;
        this.enrollmentDao = new EnrollmentDao(connection);
    }

    public void save(long userId, long courseId) {

        if (userId <= 0 || courseId <= 0) {
            throw new InvalidIdException();
        }

        try {
            if (enrollmentDao.findByUser(userId, courseId)) {
                throw new AlreadyEnrolledException();
            }
            boolean result = enrollmentDao.save(userId, courseId);
            if (result) {
                System.out.println("수강 신청 완료되었습니다.");
            } else {
                throw new EnrollmentSaveFailException();
            }
        } catch (LXPException e) {
            throw new EnrollmentSaveException();
        }
    }

    public void delete(long userId, long courseId) {

        if (userId <= 0 || courseId <= 0) {
            throw new InvalidIdException();
        }
        try {
            boolean result = enrollmentDao.delete(userId, courseId);

            if (result) {
                System.out.println("수강 취소 성공하였습니다.");
            } else {
                throw new EnrollmentDeleteFailException();
            }
        } catch (LXPException e) {
            throw new EnrollmentDeleteException();
        }
    }

    public void complete(long userId, long courseId) {

        if (userId <= 0 || courseId <= 0) {
            throw new InvalidIdException();
        }

    }

    public void isUserEnrolled(long userId, long lectureId) {

        if (userId <= 0 || lectureId <= 0) {
            throw new InvalidIdException();
        }
        try {
            boolean result = enrollmentDao.isUserEnrolled(userId, lectureId);

            if (result) {
                throw new AlreadyEnrolledException();
            } else {
                System.out.println("수강 중이 아닙니다.");
            }
        } catch (LXPException e) {
            throw new FindLectureUserException();
        }
    }
}
