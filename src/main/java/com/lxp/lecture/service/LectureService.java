package com.lxp.lecture.service;

import com.lxp.course.dao.CourseDao;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.dto.LectureCreateRequest;
import com.lxp.lecture.dto.LectureCreateResponse;
import com.lxp.lecture.model.Lecture;

public class LectureService {

    private final LectureDao lectureDao;
    private final CourseDao courseDao;

    public LectureService(LectureDao lectureDao, CourseDao courseDao) {
        this.lectureDao = lectureDao;
        this.courseDao = courseDao;
    }

    public LectureCreateResponse save(LectureCreateRequest lectureRequest) {
        if (!courseDao.existsById(lectureRequest.courseId())) {
            throw new CourseNotFoundException();
        }

        Lecture lecture = Lecture.of(
                lectureRequest.courseId(),
                lectureRequest.title(),
                lectureRequest.description(),
                lectureRequest.path()
        );
        lecture.recordTime();
        return new LectureCreateResponse(lectureDao.save(lecture));
    }

}
