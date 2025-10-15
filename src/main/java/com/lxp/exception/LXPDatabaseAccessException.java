package com.lxp.exception;

public class LXPDatabaseAccessException extends LXPException {

    public LXPDatabaseAccessException(String message) {
        super(message);
    }

    public LXPDatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
