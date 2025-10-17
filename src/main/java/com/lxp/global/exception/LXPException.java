package com.lxp.global.exception;

public class LXPException extends RuntimeException {

    public LXPException(String message) {
        super(message);
    }

    public LXPException(String message, Throwable cause) {
        super(message, cause);
    }

}
