package com.lxp.enrollment.service;

import com.lxp.enrollment.dao.EnrollmentDao;
import com.lxp.enrollment.exception.AlreadyEnrolledException;
import com.lxp.enrollment.exception.EnrollmentCompleteException;
import com.lxp.enrollment.exception.EnrollmentCompleteFailException;
import com.lxp.enrollment.exception.EnrollmentDeleteException;
import com.lxp.enrollment.exception.EnrollmentDeleteFailException;
import com.lxp.enrollment.exception.EnrollmentSaveException;
import com.lxp.enrollment.exception.EnrollmentSaveFailException;
import com.lxp.enrollment.exception.InvalidIdException;
import com.lxp.enrollment.service.dto.CreateEnrollmentDto;
import com.lxp.enrollment.service.dto.EnrollmentCourseDto;
import com.lxp.enrollment.service.dto.EnrollmentLectureCheckDto;

import java.util.List;

public class EnrollmentService {

    private final EnrollmentDao enrollmentDao;

    public EnrollmentService(EnrollmentDao enrollmentDao) {
        this.enrollmentDao = enrollmentDao;
    }

    public void save(CreateEnrollmentDto dto) {
        try {
            if (enrollmentDao.findByUser(dto.userId(), dto.courseId())) {
                throw new AlreadyEnrolledException();
            }
            boolean result = enrollmentDao.save(dto.userId(), dto.courseId());
            if (!result) {
                throw new EnrollmentSaveFailException();
            }
        } catch (AlreadyEnrolledException | EnrollmentSaveFailException e) {
            throw e;
        } catch (Exception e) {
            throw new EnrollmentSaveException(e);
        }
    }

    public void delete(CreateEnrollmentDto dto) {
        try {
            boolean result = enrollmentDao.delete(dto.userId(), dto.courseId());
            if (!result) {
                throw new EnrollmentDeleteFailException();
            }
        } catch (EnrollmentDeleteFailException e) {
            throw e;
        } catch (Exception e) {
            throw new EnrollmentDeleteException(e);
        }
    }

    public void complete(CreateEnrollmentDto dto) {
        try {
            boolean result = enrollmentDao.complete(dto.userId(), dto.courseId());
            if (!result) {
                throw new EnrollmentCompleteFailException();
            }
        } catch (EnrollmentCompleteFailException e) {
            throw e;
        } catch (Exception e) {
            throw new EnrollmentCompleteException(e);
        }
    }

    public boolean isUserEnrolled(EnrollmentLectureCheckDto dto) {
        if (dto == null || dto.userId() <= 0 || dto.lectureId() <= 0) {
            throw new InvalidIdException();
        }
        return enrollmentDao.isUserEnrolled(dto.userId(), dto.lectureId());
    }


    public List<EnrollmentCourseDto> findCoursesByUser(long userId) {
        if (userId <= 0) {
            throw new InvalidIdException();
        }
        return enrollmentDao.findCoursesByUser(userId);
    }

}
