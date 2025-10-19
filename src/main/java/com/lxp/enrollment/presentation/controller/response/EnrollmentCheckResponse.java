package com.lxp.enrollment.presentation.controller.response;

public record EnrollmentCheckResponse(boolean enrolled) {

    @Override
    public String toString() {
        return enrolled ? "수강 중입니다." : "수강하지 않았습니다.";
    }
}
