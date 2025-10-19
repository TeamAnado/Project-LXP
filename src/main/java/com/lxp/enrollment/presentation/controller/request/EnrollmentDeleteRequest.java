package com.lxp.enrollment.presentation.controller.request;

import com.lxp.enrollment.exception.InvalidIdException;
import com.lxp.enrollment.service.dto.CreateEnrollmentDto;

public record EnrollmentDeleteRequest(Long courseId) {

    public EnrollmentDeleteRequest {
        if (courseId == null || courseId <= 0) {
            throw new InvalidIdException();
        }
    }

    public CreateEnrollmentDto to(long userId) {
        return new CreateEnrollmentDto(userId, courseId);
    }

    @Override
    public String toString() {
        return "EnrollmentDeleteRequest{" +
                "courseId=" + courseId +
                '}';
    }
}
