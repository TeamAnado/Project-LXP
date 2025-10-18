package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class EnrollmentSaveException extends LXPException {
    public EnrollmentSaveException() {
        super("수강 신청중 오류가 발생했습니다.");
    }
}
