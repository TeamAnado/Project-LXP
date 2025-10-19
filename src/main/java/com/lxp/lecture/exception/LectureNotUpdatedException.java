package com.lxp.lecture.exception;

import com.lxp.global.exception.LXPDatabaseAccessException;

public class LectureNotUpdatedException extends LXPDatabaseAccessException {

    public LectureNotUpdatedException() {
        super("강의 수정 중 데이터베이스 오류가 발생했습니다.");
    }

    public LectureNotUpdatedException(Throwable cause) {
        super("강의 수정 중 데이터베이스 오류가 발생했습니다.", cause);
    }

}
