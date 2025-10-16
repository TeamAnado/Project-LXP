package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class UserAlreadyExistsException extends LXPException {

    public UserAlreadyExistsException() {
        super("이미 가입된 이메일입니다.");
    }

}
