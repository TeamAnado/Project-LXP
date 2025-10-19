package com.lxp.enrollment.presentation.controller;

import com.lxp.enrollment.presentation.controller.request.EnrollmentCheckRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentCompleteRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentDeleteRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentSaveRequest;
import com.lxp.enrollment.presentation.controller.response.EnrollmentCheckResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentCompleteResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentDeleteResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentFindResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentSaveResponse;
import com.lxp.enrollment.service.EnrollmentService;
import com.lxp.enrollment.service.dto.EnrollmentCourseDto;

import java.util.List;

public class EnrollmentController {
    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    public EnrollmentSaveResponse save(EnrollmentSaveRequest request, long userId) {
        service.save(request.to(userId));
        return new EnrollmentSaveResponse(true, "수강 신청이 완료되었습니다.");
    }

    public EnrollmentDeleteResponse delete(EnrollmentDeleteRequest request, long userId) {
        service.delete(request.to(userId));
        return new EnrollmentDeleteResponse(true, "수강 취소가 완료되었습니다.");
    }

    public EnrollmentCompleteResponse complete(EnrollmentCompleteRequest request, long userId) {
        service.complete(request.to(userId));
        return new EnrollmentCompleteResponse(true, "수강 완료 처리되었습니다.");
    }

    public EnrollmentFindResponse findCoursesByUser(long userId) {
        List<EnrollmentCourseDto> courses = service.findCoursesByUser(userId);
        return new EnrollmentFindResponse(courses);
    }


    public EnrollmentCheckResponse isUserEnrolled(EnrollmentCheckRequest request, long userId) {
        boolean enrolled = service.isUserEnrolled(request.to(userId));
        return new EnrollmentCheckResponse(enrolled);
    }
}
