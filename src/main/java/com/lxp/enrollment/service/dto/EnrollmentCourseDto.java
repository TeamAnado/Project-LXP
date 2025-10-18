package com.lxp.enrollment.service.dto;

import com.lxp.enrollment.exception.InvalidIdException;

public record EnrollmentCourseDto(long courseId, String title, String description, int state) {
    public EnrollmentCourseDto {
        if (courseId <= 0) throw new InvalidIdException();

    }
}
