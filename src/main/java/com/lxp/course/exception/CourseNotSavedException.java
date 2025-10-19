package com.lxp.course.exception;

import com.lxp.global.exception.LXPDatabaseAccessException;

public class CourseNotSavedException extends LXPDatabaseAccessException {

    public CourseNotSavedException() {
        super("강의 저장에 실패했습니다.");
    }

    public CourseNotSavedException(Throwable cause) {
        super("강의 저장에 실패했습니다.", cause);
    }

}