package com.lxp.enrollment.exception;

import com.lxp.global.exception.LXPException;

public class InvalidIdException extends LXPException {
    public InvalidIdException() {
        super("유효하지 않은 ID입니다.");
    }

    public InvalidIdException(Throwable cause) {
        super("유효하지 않은 ID입니다.", cause);
    }
}
