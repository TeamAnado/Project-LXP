package com.lxp.enrollment.presentation.controller.response;

import com.lxp.enrollment.service.dto.EnrollmentCourseDto;

import java.util.List;

public record EnrollmentFindResponse(List<EnrollmentCourseDto> courses) {

    public int count() {
        return courses == null ? 0 : courses.size();
    }

    @Override
    public String toString() {
        return "EnrollmentFindResponse{" +
                "count=" + count() +
                ", courses=" + courses +
                '}';
    }
}
