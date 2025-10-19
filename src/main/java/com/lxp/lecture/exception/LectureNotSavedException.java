package com.lxp.lecture.exception;

import com.lxp.global.exception.LXPDatabaseAccessException;

public class LectureNotSavedException extends LXPDatabaseAccessException {

    public LectureNotSavedException() {
        super("강의 저장 중 데이터베이스 오류가 발생했습니다.");
    }

    public LectureNotSavedException(Throwable cause) {
        super("강의 저장 중 데이터베이스 오류가 발생했습니다.", cause);
    }

}
