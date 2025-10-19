package com.lxp.course.exception;

import com.lxp.global.exception.LXPDatabaseAccessException;

public class CourseNotUpdatedException extends LXPDatabaseAccessException {

    public CourseNotUpdatedException() {
        super("강의 수정에 실패했습니다.");
    }

    public CourseNotUpdatedException(Throwable cause) {
        super("강의 수정에 실패했습니다.", cause);
    }

}