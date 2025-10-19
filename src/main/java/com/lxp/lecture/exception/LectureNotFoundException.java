package com.lxp.lecture.exception;

import com.lxp.global.exception.LXPException;

public class LectureNotFoundException extends LXPException {

    public LectureNotFoundException() {
        super("해당 ID의 강의를 찾을 수 없습니다.");
    }

}
