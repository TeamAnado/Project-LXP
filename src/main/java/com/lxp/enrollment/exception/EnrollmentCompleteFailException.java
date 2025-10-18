package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class EnrollmentCompleteFailException extends LXPException {
    public EnrollmentCompleteFailException() {
        super("수강 기록이 없거나 이미 완료한 상태입니다.");
    }

    public EnrollmentCompleteFailException(Throwable cause) {
        super("수강 기록이 없거나 이미 완료한 상태입니다.", cause);
    }
}
