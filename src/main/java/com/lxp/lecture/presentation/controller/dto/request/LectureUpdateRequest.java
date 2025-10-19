package com.lxp.lecture.presentation.controller.dto.request;

import com.lxp.lecture.service.dto.LectureUpdateDto;

public record LectureUpdateRequest(
        long id,
        Long courseId,
        String title,
        String description,
        String path
) {

    public LectureUpdateDto to() {
        return new LectureUpdateDto(this.id, this.courseId, this.title, this.description, this.path);
    }

}
