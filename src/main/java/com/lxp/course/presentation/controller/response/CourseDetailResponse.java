package com.lxp.course.presentation.controller.response;

import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.CourseDetailDto;

import java.time.LocalDateTime;

public record CourseDetailResponse(
        Long id,
        String title,
        String description,
        Category category,
        Long instructorId,
        LocalDateTime dateCreated
) {

    public static CourseDetailResponse from(CourseDetailDto dto) {
        return new CourseDetailResponse(
                dto.id(),
                dto.title(),
                dto.description(),
                dto.category(),
                dto.instructorId(),
                dto.dateCreated()
        );
    }

}