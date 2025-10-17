package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class InvalidCourseIdException extends LXPException {

    public InvalidCourseIdException() {
        super("코스 ID는 null이거나 0 이하일 수 없습니다.");
    }

}
