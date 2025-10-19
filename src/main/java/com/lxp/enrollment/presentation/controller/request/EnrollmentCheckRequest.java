package com.lxp.enrollment.presentation.controller.request;

import com.lxp.enrollment.exception.InvalidIdException;
import com.lxp.enrollment.service.dto.EnrollmentLectureCheckDto;

public record EnrollmentCheckRequest(Long lectureId) {
    public EnrollmentCheckRequest {
        if (lectureId == null || lectureId <= 0) {
            throw new InvalidIdException();
        }
    }

    public EnrollmentLectureCheckDto to(long userId) {
        return new EnrollmentLectureCheckDto(userId, this.lectureId);
    }

    @Override
    public String toString() {
        return "EnrollmentCheckRequest{" +
                "lectureId=" + lectureId +
                '}';
    }
}
