package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class EnrollmentDeleteFailException extends LXPException {
    public EnrollmentDeleteFailException() {
        super("수강 취소에 실패하였습니다.");
    }

    public EnrollmentDeleteFailException(Throwable cause) {
        super("수강 취소에 실패하였습니다.", cause);
    }
}
