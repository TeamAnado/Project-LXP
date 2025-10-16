package com.lxp.lecture.exception;

import com.lxp.exception.LXPException;

public class InvalidSectionIdException extends LXPException {
    
    public InvalidSectionIdException() {
        super("섹션 ID는 null일 수 없습니다.");
    }
}
