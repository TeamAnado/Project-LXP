package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class EnrollmentDeleteException extends LXPException {
    public EnrollmentDeleteException() {
        super("수강 취소중 오류가 발생하였습니다.");
    }
}
