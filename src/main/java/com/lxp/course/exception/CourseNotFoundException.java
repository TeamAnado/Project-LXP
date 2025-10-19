package com.lxp.course.exception;

import com.lxp.global.exception.LXPException;

public class CourseNotFoundException extends LXPException {

    public CourseNotFoundException() {
        super("강좌를 찾을 수 없습니다.");
    }

    public CourseNotFoundException(Throwable cause) {
        super("강좌를 찾을 수 없습니다.", cause);
    }

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
