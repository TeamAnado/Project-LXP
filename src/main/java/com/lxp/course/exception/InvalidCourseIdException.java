package com.lxp.course.exception;

import com.lxp.global.exception.LXPException;

public class InvalidCourseIdException extends LXPException {

    public InvalidCourseIdException() {
        super("잘못된 강좌 ID 입니다.");
    }

}
