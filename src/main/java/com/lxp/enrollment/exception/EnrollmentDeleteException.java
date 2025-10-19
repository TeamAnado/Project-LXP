package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class EnrollmentDeleteException extends LXPException {
    public EnrollmentDeleteException() {
        super("수강 취소 처리 중 오류가 발생했습니다.");
    }

    public EnrollmentDeleteException(Throwable cause) {
        super("수강 취소 처리 중 오류가 발생했습니다.", cause);
    }
}
