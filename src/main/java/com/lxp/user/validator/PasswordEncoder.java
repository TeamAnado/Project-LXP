package com.lxp.user.validator;

import com.lxp.exception.LXPExceptionHandler;
import com.lxp.support.Hashing;

public class PasswordEncoder {

    public String encode(String password) {
        String hashed = null;
        try {
            hashed = Hashing.encode(password);
        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
        return hashed;
    }

    public boolean check(String oldPassword, String newPassword) {
        boolean flag = false;
        try {
            flag = Hashing.matches(newPassword, oldPassword);
        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
        return flag;
    }
}
