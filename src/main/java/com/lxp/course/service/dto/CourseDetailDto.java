package com.lxp.course.service.dto;

import com.lxp.course.model.enums.Category;

import java.time.LocalDateTime;

public record CourseDetailDto(
        Long id,
        String title,
        String description,
        Category category,
        Long instructorId,
        LocalDateTime dateCreated
) {
}
