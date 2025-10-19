package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class EnrollmentSaveFailException extends LXPException {
    public EnrollmentSaveFailException() {
        super("수강 신청에 실패했습니다.");
    }

    public EnrollmentSaveFailException(Throwable cause) {
        super("수강 신청에 실패했습니다.", cause);
    }
}
