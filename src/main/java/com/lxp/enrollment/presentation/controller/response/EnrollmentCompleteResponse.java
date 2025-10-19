package com.lxp.enrollment.presentation.controller.response;

public record EnrollmentCompleteResponse(boolean success, String message) {

    @Override
    public String toString() {
        return "EnrollmentCompleteResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
