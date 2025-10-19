package com.lxp.lecture.service;

import com.lxp.course.dao.CourseDAO;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.global.exception.LXPDatabaseAccessException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.exception.LectureNotFoundException;
import com.lxp.lecture.model.Lecture;
import com.lxp.lecture.presentation.controller.dto.response.LectureCreateResponse;
import com.lxp.lecture.service.dto.LectureSaveDto;
import com.lxp.lecture.service.dto.LectureUpdateDto;
import java.sql.SQLException;

public class LectureService {

    private final LectureDao lectureDao;
    private final CourseDAO courseDao;

    public LectureService(LectureDao lectureDao, CourseDAO courseDao) {
        this.lectureDao = lectureDao;
        this.courseDao = courseDao;
    }

    public LectureCreateResponse save(LectureSaveDto dto) {
        validateCourseSave(dto);

        Lecture lecture = dto.to();
        long newId = lectureDao.save(lecture);
        Lecture savedLecture = lecture.withId(newId);

        return LectureCreateResponse.to(savedLecture);
    }

    private void validateCourseSave(LectureSaveDto dto) {
        try {
            if (!courseDao.existsById(dto.courseId())) {
                throw new CourseNotFoundException();
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강좌 조회 중 오류가 발생했습니다.", e);
        }
    }

    public void update(LectureUpdateDto dto) {
        Lecture existingLecture = lectureDao.findById(dto.id())
                .orElseThrow(LectureNotFoundException::new);

        validateCourseUpdate(dto);
        Lecture updatedLecture = dto.to(existingLecture);

        lectureDao.update(updatedLecture);
    }

    private void validateCourseUpdate(LectureUpdateDto dto) {
        Long newCourseId = dto.courseId();
        try {
            if (newCourseId != null && !courseDao.existsById(newCourseId)) {
                throw new CourseNotFoundException();
            }
        } catch (SQLException e) {
            throw new LXPDatabaseAccessException("강좌 수정 중 오류가 발생했습니다.", e);
        }
    }

}
