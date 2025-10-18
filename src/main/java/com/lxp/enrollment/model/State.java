package com.lxp.enrollment.model;

public enum State {
    IN_PROGRESS(1),   // 수강 중
    CANCELLED(2),     // 취소됨
    COMPLETED(3);     // 완료됨

    private final int code;

    State(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
