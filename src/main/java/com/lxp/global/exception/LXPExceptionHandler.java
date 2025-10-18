package com.lxp.global.exception;

import java.util.function.Supplier;

public class LXPExceptionHandler {

    private static final int MAX_RETRY_COUNT = 3;

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
        int attempts = 0;
        while (attempts < MAX_RETRY_COUNT) {
            try {
                return callback.get();
            } catch (Exception e) {
                handle(e);
                attempts++;
                if (attempts >= MAX_RETRY_COUNT) {
                    throw new LXPException("최대 재시도 횟수(" + MAX_RETRY_COUNT + ")를 초과했습니다.", e);
                }
            }
        }
        throw new LXPException("재시도 실패");
    }

    private static String getTimestamp() {
        return java.time.LocalTime.now().toString();
    }

    private static boolean isCauseNull(Exception e) {
        return e.getCause() == null;
    }
}
