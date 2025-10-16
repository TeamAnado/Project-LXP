package com.lxp.course.service.dto;

import com.lxp.course.model.enums.Category;

public record CourseListDto(
        Long id,
        String title,
        Category category,
        Long instructorId
) {
}
