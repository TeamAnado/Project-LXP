package com.lxp.course.service.dto;

import com.lxp.course.model.enums.Category;

public record UpdateCourseDto(
        Long id,
        String title,
        String description,
        Category category
) {}
