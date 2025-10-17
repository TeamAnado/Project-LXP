package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class UserNotSavedException extends LXPException {

    public UserNotSavedException() {
        super("회원가입 중 데이터베이스 작업에 실패하였습니다");
    }

    public UserNotSavedException(Exception e) {
        super("회원가입 중 데이터베이스 작업에 실패하였습니다", e);
    }

}
