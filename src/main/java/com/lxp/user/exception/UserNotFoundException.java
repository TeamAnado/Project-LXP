package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class UserNotFoundException extends LXPException {

    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }

    public UserNotFoundException(Throwable cause) {
        super("사용자를 찾을 수 없습니다.", cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
