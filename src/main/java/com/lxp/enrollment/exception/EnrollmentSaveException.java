package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class EnrollmentSaveException extends LXPException {
    public EnrollmentSaveException() {
        super("수강 신청 처리중 오류가 발생했습니다.");
    }

    public EnrollmentSaveException(Throwable cause) {
        super("수강 신청 처리중 오류가 발생했습니다.", cause);
    }
}
