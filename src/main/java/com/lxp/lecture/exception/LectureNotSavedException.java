package com.lxp.lecture.exception;

import com.lxp.exception.LXPDatabaseAccessException;

public class LectureNotSavedException extends LXPDatabaseAccessException {

    public LectureNotSavedException() {
        super("강의 저장에 실패했습니다.");
    }

    public LectureNotSavedException(Throwable cause) {
        super("강의 저장에 실패했습니다.", cause);
    }

}
