package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class UserNotFoundException extends LXPException {

    public UserNotFoundException(Throwable cause) {
        super("", cause);
    }

}
