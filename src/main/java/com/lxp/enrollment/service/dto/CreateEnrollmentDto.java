package com.lxp.enrollment.service.dto;

import com.lxp.enrollment.exception.InvalidIdException;

public record CreateEnrollmentDto(long userId, long courseId) {
    public CreateEnrollmentDto {
        if (userId <= 0 || courseId <= 0) throw new InvalidIdException();
    }

}
