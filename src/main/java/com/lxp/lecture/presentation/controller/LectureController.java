package com.lxp.lecture.presentation.controller;

import com.lxp.lecture.presentation.controller.dto.response.LectureCreateResponse;
import com.lxp.lecture.service.LectureService;
import com.lxp.lecture.service.dto.LectureListDto;
import com.lxp.lecture.service.dto.LectureSaveDto;
import com.lxp.lecture.service.dto.LectureUpdateDto;
import java.util.List;

public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public LectureCreateResponse createLecture(LectureSaveDto saveDto) {
        return lectureService.save(saveDto);
    }

    public void updateLecture(LectureUpdateDto updateDto) {
        lectureService.update(updateDto);
    }

    public void deleteLecture(long lectureId) {
        lectureService.deleteLecture(lectureId);
    }

    public List<LectureListDto> findLecturesByCourseId(long courseId) {
        return lectureService.findLecturesByCourseId(courseId);
    }

}
