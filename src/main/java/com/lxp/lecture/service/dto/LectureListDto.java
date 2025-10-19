package com.lxp.lecture.service.dto;

import com.lxp.lecture.model.Lecture;

public record LectureListDto(long lectureId, String title) {

    public LectureListDto(Lecture lecture) {
        this(lecture.getId(), lecture.getTitle());
    }

}
