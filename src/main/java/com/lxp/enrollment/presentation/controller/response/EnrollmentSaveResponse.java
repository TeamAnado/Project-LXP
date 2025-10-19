package com.lxp.enrollment.presentation.controller.response;

public record EnrollmentSaveResponse(boolean success, String message) {

    @Override
    public String toString() {
        return "EnrollmentSaveResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
