package com.lxp.course.exception;

import com.lxp.global.exception.LXPDatabaseAccessException;

public class CourseNotDeletedException extends LXPDatabaseAccessException {

    public CourseNotDeletedException() {
        super("강의 삭제에 실패했습니다.");
    }

    public CourseNotDeletedException(Throwable cause) {
        super("강의 삭제에 실패했습니다.", cause);
    }

}