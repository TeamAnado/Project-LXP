package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class AlreadyEnrolledException extends LXPException {
    public AlreadyEnrolledException() {
        super("이미 수강 중인 강좌 입니다.");
    }
}
