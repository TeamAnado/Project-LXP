package com.lxp.enrollment.exception;


import com.lxp.global.exception.LXPException;

public class FindUserException extends LXPException {

    public FindUserException() {
        super("수강 여부 조회 확인중 오류가 발생했습니다.");
    }

    public FindUserException(Throwable cause) {
        super("수강 여부 조회 확인중 오류가 발생했습니다.", cause);
    }
}
