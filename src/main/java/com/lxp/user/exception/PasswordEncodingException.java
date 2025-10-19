package com.lxp.user.exception;

import com.lxp.global.exception.LXPException;

public class PasswordEncodingException extends LXPException {

    public PasswordEncodingException(Throwable cause) {
        super("비밀번호 암호화 오류", cause);
    }

    public PasswordEncodingException(String message) {
        super(message);
    }

    public PasswordEncodingException(String message, Throwable cause) {
        super(message, cause);
    }

}
