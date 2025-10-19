package com.lxp.enrollment.presentation.controller.response;

public record EnrollmentDeleteResponse(boolean success, String message) {

    @Override
    public String toString() {
        return "EnrollmentDeleteResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
