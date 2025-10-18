package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class EnrollmentDeleteFailException extends LXPException {
    public EnrollmentDeleteFailException() {
        super("수강 취소에 실패하였습니다.");
    }
}
