package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class InvalidTitleException extends LXPException {

    public InvalidTitleException() {
        super("강의 제목은 비어 있을 수 없습니다.");
    }
    
}
