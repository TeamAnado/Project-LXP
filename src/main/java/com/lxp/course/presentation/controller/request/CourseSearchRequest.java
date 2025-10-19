package com.lxp.course.presentation.controller.request;

import com.lxp.course.model.enums.Category;

public record CourseSearchRequest(
        String title,
        Category category,
        Long instructorId
) {

}