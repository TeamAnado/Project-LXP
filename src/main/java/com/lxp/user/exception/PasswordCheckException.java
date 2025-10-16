package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class PasswordCheckException extends LXPException {

    public PasswordCheckException(Throwable cause) {
        super("비밀번호 검증 실패", cause);
    }

    public PasswordCheckException(String message) {
        super(message);
    }

    public PasswordCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
