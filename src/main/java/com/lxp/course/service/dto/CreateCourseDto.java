package com.lxp.course.service.dto;

import com.lxp.course.model.enums.Category;

public record CreateCourseDto(
        String title,
        String description,
        Long instructorId,
        Category category
) {
}
