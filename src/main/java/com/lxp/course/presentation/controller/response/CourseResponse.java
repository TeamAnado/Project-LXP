package com.lxp.course.presentation.controller.response;

import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.CourseListDto;

public record CourseResponse(
        Long id,
        String title,
        Category category,
        Long instructorId
) {

    public static CourseResponse from(CourseListDto dto) {
        return new CourseResponse(dto.id(), dto.title(), dto.category(), dto.instructorId());
    }

}