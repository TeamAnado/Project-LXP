package com.lxp.lecture.service;

import com.lxp.course.dao.CourseDao;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.exception.LectureNotFoundException;
import com.lxp.lecture.model.Lecture;
import com.lxp.lecture.presentation.controller.dto.response.LectureCreateResponse;
import com.lxp.lecture.service.dto.LectureListDto;
import com.lxp.lecture.service.dto.LectureSaveDto;
import com.lxp.lecture.service.dto.LectureUpdateDto;
import java.util.List;
import java.util.stream.Collectors;

public class LectureService {

    private final LectureDao lectureDao;
    private final CourseDao courseDao;

    public LectureService(LectureDao lectureDao, CourseDao courseDao) {
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
        if (!courseDao.existsById(dto.courseId())) {
            throw new CourseNotFoundException();
        }
    }

    public List<LectureListDto> findLecturesByCourseId(long courseId) {
        return lectureDao.findLecturesByCourseId(courseId).stream()
                .map(LectureListDto::new)
                .collect(Collectors.toList());
    }

    public void deleteLecture(long lectureId) {
        lectureDao.delete(lectureId);
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
        if (newCourseId != null && !courseDao.existsById(newCourseId)) {
            throw new CourseNotFoundException();
        }
    }

}
