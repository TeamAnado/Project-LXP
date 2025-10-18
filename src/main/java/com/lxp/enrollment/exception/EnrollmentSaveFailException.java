package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class EnrollmentSaveFailException extends LXPException {
    public EnrollmentSaveFailException() {
        super("수강 신청에 실패하였습니다.");
    }
}
