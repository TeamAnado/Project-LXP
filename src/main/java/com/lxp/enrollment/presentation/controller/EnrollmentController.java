package com.lxp.enrollment.presentation.controller;

import com.lxp.enrollment.exception.EnrollmentCompleteFailException;
import com.lxp.enrollment.exception.EnrollmentDeleteFailException;
import com.lxp.enrollment.exception.EnrollmentSaveFailException;
import com.lxp.enrollment.exception.FindLectureUserException;
import com.lxp.enrollment.presentation.controller.request.EnrollmentCheckRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentCompleteRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentDeleteRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentSaveRequest;
import com.lxp.enrollment.service.EnrollmentService;
import com.lxp.global.exception.LXPException;

public class EnrollmentController {
    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    public boolean save(EnrollmentSaveRequest request, long userId) {
        try {
            service.save(request.to(userId));
            System.out.println("수강 신청이 완료되었습니다.");
            return true;
        } catch (LXPException e) {
            throw new EnrollmentSaveFailException(e);
        }
    }

    public boolean delete(EnrollmentDeleteRequest request, long userId) {
        try {
            service.delete(request.to(userId));
            System.out.println("수강 취소가 완료되었습니다.");
            return true;
        } catch (LXPException e) {
            throw new EnrollmentDeleteFailException(e);
        }
    }

    public boolean complete(EnrollmentCompleteRequest request, long userId) {
        try {
            service.complete(request.to(userId));
            System.out.println("수강 완료 처리되었습니다.");
            return true;
        } catch (LXPException e) {
            throw new EnrollmentCompleteFailException(e);
        }
    }

    public void findCoursesByUser(long userId) {
        service.findCoursesByUser(userId);
    }

    public boolean isUserEnrolled(EnrollmentCheckRequest request, long userId) {
        try {
            return service.isUserEnrolled(request.to(userId));
        } catch (LXPException e) {
            throw new FindLectureUserException(e);
        }
    }
}
