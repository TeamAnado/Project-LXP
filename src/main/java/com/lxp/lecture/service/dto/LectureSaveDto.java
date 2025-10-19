package com.lxp.lecture.service.dto;

import com.lxp.lecture.model.Lecture;

public record LectureSaveDto(
        Long courseId,
        String title,
        String description,
        String path
) {

    public Lecture to() {
        Lecture lecture = new Lecture(courseId, title, description, path);
        lecture.recordTime();
        return lecture;
    }
    
}
