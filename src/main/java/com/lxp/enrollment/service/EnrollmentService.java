package com.lxp.enrollment.service;

import com.lxp.enrollment.dao.EnrollmentDao;
import com.lxp.enrollment.exception.AlreadyEnrolledException;
import com.lxp.enrollment.exception.EnrollmentCompleteException;
import com.lxp.enrollment.exception.EnrollmentCompleteFailException;
import com.lxp.enrollment.exception.EnrollmentDeleteException;
import com.lxp.enrollment.exception.EnrollmentDeleteFailException;
import com.lxp.enrollment.exception.EnrollmentSaveException;
import com.lxp.enrollment.exception.EnrollmentSaveFailException;
import com.lxp.enrollment.exception.FindLectureUserException;
import com.lxp.enrollment.exception.InvalidIdException;
import com.lxp.enrollment.service.dto.CreateEnrollmentDto;
import com.lxp.enrollment.service.dto.EnrollmentCourseDto;
import com.lxp.enrollment.service.dto.EnrollmentLectureCheckDto;
import com.lxp.global.exception.LXPException;

import java.util.List;

public class EnrollmentService {

    private final EnrollmentDao enrollmentDao;

    public EnrollmentService(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    public void save(CreateEnrollmentDto dto) {
        try {
            if (this.enrollmentDao.findByUser(dto.userId(), dto.courseId())) {
                throw new AlreadyEnrolledException();
            }
            boolean result = this.enrollmentDao.save(dto.userId(), dto.courseId());
            if (!result) {
                throw new EnrollmentSaveFailException();
            }
            System.out.println("수강 신청 완료되었습니다.");

        } catch (AlreadyEnrolledException | EnrollmentSaveFailException e) {
            throw e;

        } catch (Exception e) {
            throw new EnrollmentSaveException(e);
        }
    }

    public void delete(CreateEnrollmentDto dto) {
        try {
            boolean result = this.enrollmentDao.delete(dto.userId(), dto.courseId());

            if (!result) {
                throw new EnrollmentDeleteFailException();
            }
            System.out.println("수강 취소 성공하였습니다.");

        } catch (EnrollmentDeleteFailException e) {
            throw e;
        } catch (Exception e) {
            throw new EnrollmentDeleteException(e);
        }
    }

    public void complete(CreateEnrollmentDto dto) {
        try {
            boolean result = this.enrollmentDao.complete(dto.userId(), dto.courseId());

            if (!result) {
                throw new EnrollmentCompleteFailException();
            }
            System.out.println("수강 완료 처리되었습니다.");

        } catch (EnrollmentCompleteFailException e) {
            throw e;

        } catch (Exception e) {
            throw new EnrollmentCompleteException(e);
        }
    }

    public boolean isUserEnrolled(EnrollmentLectureCheckDto dto) {
        if ((dto == null || dto.userId() <= 0 || dto.lectureId() <= 0)) {
            throw new InvalidIdException();
        }
        try {
            return this.enrollmentDao.isUserEnrolled(dto.userId(), dto.lectureId());
        } catch (LXPException e) {
            throw new FindLectureUserException(e);
        }
    }

    public void findCoursesByUser(long userId) {
        if (userId <= 0) {
            throw new InvalidIdException();
        }
        List<EnrollmentCourseDto> courses = this.enrollmentDao.findCoursesByUser(userId);

        if (courses.isEmpty()) {
            System.out.println("현재 수강 중이거나 완료된 강좌가 없습니다.");
            return;
        }

        System.out.println("=== 나의 강좌 목록 ===");
        for (EnrollmentCourseDto c : courses) {
            String stateLabel = switch (c.state()) {
                case 1 -> "수강중";
                case 3 -> "수강완료";
                default -> "취소된 강좌";
            };
            System.out.printf("%d. %s [%s]%n", c.courseId(), c.title(), stateLabel);
        }
    }
}


