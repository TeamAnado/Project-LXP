package com.lxp.lecture.presentation.controller.dto.response;

import com.lxp.lecture.model.Lecture;

public record LectureCreateResponse(
        Long id
) {

    public static LectureCreateResponse to(Lecture lecture) {
        return new LectureCreateResponse(lecture.getId());
    }

}
