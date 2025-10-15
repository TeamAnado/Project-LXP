package com.lxp.exception;

public class LXPException extends RuntimeException {

    public LXPException(String message) {
        super(message);
    }

    public LXPException(String message, Throwable cause) {
        super(message, cause);
    }

    public static LXPException create(String message) {
        return new LXPException(message);
    }

    public static LXPException create(String message, Throwable cause) {
        return new LXPException(message, cause);
    }
}
