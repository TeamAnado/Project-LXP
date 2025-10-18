package com.lxp.user.exception;

import com.lxp.global.exception.LXPException;

public class InvalidEmailException extends LXPException {

    public InvalidEmailException() {
        super("잘못된 이메일 형식입니다.");
    }

    public InvalidEmailException(String message) {
        super(message);
    }

}
