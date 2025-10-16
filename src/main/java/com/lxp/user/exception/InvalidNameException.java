package com.lxp.user.exception;

import com.lxp.exception.LXPException;

public class InvalidNameException extends LXPException {

    public InvalidNameException() {
        super("빈 칸이나 공백으로 이름을 설정할 수 없습니다.");
    }

}
