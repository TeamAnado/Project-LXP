package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class InvalidPathException extends LXPException {

    public InvalidPathException() {
        super("경로는 비어 있을 수 없습니다.");
    }

}
