package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class EnrollmentCompleteException extends LXPException {
    public EnrollmentCompleteException() {
        super("수강 완료 처리중 오류가 발생했습니다.");
    }

    public EnrollmentCompleteException(Throwable cause) {
        super("수강 완료 처리중 오류가 발생했습니다.", cause);
    }
}
