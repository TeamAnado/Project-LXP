package com.lxp.lecture.presentation.controller.dto.request;

import com.lxp.global.exception.LXPException;
import com.lxp.lecture.service.dto.LectureUpdateDto;

public record LectureUpdateRequest(
        long id,
        Long courseId,
        String title,
        String description,
        String path
) {

    public LectureUpdateRequest {
        if (id <= 0) {
            throw new LXPException("강의 ID는 0 이하일 수 없습니다.");
        }
    }

    public LectureUpdateDto to() {
        return new LectureUpdateDto(this.id, this.courseId, this.title, this.description, this.path);
    }

}
