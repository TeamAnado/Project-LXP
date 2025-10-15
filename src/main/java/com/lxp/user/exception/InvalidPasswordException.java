package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class InvalidPasswordException extends LXPException {

    public InvalidPasswordException() {
        super("잘못된 비밀번호 형식입니다.");
    }
}
