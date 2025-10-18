package com.lxp.global.exception;

import java.util.function.Supplier;

public class LXPExceptionHandler {

    public static void handle(Exception e) {
        System.err.println("⚠️ [" + getTimestamp() + "] ERROR: " + e.getMessage());
        printStackTrace(e);
    }

    private static void printStackTrace(Exception e) {
        if (!isCauseNull(e)) {
            System.err.println("  Caused by: " + e.getCause().getMessage());
            e.getCause().printStackTrace();
            return;
        }
        e.printStackTrace();
    }

    public static <T> T executeWithRetry(Supplier<T> callback) {
        while (true) {
            try {
                return callback.get();
            } catch (Exception e) {
                handle(e);
            }
        }
    }

    private static String getTimestamp() {
        return java.time.LocalTime.now().toString();
    }

    private static boolean isCauseNull(Exception e) {
        return e.getCause() == null;
    }
}
