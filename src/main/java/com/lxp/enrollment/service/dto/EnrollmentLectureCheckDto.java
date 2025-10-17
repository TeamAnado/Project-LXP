package com.lxp.enrollment.service.dto;

import com.lxp.enrollment.exception.InvalidIdException;

public record EnrollmentLectureCheckDto(long userId, long lectureId) {
    public EnrollmentLectureCheckDto {
        if (userId <= 0 || lectureId <= 0) throw new InvalidIdException();
    }
}
