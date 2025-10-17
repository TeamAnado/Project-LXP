package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class InvalidDescriptionException extends LXPException {

    public InvalidDescriptionException() {
        super("강의 설명은 비어 있을 수 없습니다.");
    }

}
