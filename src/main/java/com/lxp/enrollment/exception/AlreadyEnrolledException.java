package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class AlreadyEnrolledException extends LXPException {
    public AlreadyEnrolledException() {
        super("이미 수강 중인 강좌입니다.");
    }

    public AlreadyEnrolledException(Throwable cause) {
        super("이미 수강 중인 강좌입니다.", cause);
    }
}
