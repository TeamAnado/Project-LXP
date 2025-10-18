package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class FindUserException extends LXPException {

    public FindUserException() {
        super("수강 여부 조회 중 오류가 발생하였습니다.");
    }
}
