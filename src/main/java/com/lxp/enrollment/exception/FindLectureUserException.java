package com.lxp.enrollment.exception;

import com.lxp.exception.LXPException;

public class FindLectureUserException extends LXPException {
    public FindLectureUserException() {
        super("강의 접근 권한 확인 중 오류가 발생하였습니다.");
    }
}
