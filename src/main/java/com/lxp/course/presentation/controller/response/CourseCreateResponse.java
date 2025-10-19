package com.lxp.course.presentation.controller.response;

public record CourseCreateResponse(
        Long id,
        String message
) {

    public static CourseCreateResponse from(Long id) {
        return new CourseCreateResponse(id, "강의가 성공적으로 생성되었습니다.");
    }

}