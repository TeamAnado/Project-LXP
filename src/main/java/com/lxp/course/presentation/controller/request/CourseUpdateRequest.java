package com.lxp.course.presentation.controller.request;

import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.UpdateCourseDto;

public record CourseUpdateRequest(
        Long id,
        String title,
        String description,
        Category category
) {

    public UpdateCourseDto to() {
        return new UpdateCourseDto(id, title, description, category);
    }

}