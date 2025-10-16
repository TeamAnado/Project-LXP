package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class FailedToRetrieveIdException extends LXPException {
    
    public FailedToRetrieveIdException() {
        super("강의 생성 후 ID를 받아오지 못했습니다.");
    }
}
