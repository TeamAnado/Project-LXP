package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class LectureNotSavedException extends LXPException {
    
    public LectureNotSavedException() {
        super("강의 저장에 실패했습니다. (0 rows affected)");
    }

    public LectureNotSavedException(Exception e) {
        super("강의 생성에 실패했습니다.", e);
    }
}
