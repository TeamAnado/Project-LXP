package com.lxp.course.presentation.controller.request;

import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.CreateCourseDto;

public record CourseCreateRequest(
        String title,
        String description,
        Long instructorId,
        Category category
) {

    public CreateCourseDto to() {
        return new CreateCourseDto(title, description, instructorId, category);
    }

}