package com.lxp.user.validator;

import com.lxp.support.Hashing;
import com.lxp.user.exception.PasswordCheckException;
import com.lxp.user.exception.PasswordEncodingException;

public class PasswordEncoder {

    public String encode(String password) {
        try {
            return Hashing.encode(password);
        } catch (Exception e) {
            throw new PasswordEncodingException(e);
        }
    }

    public boolean check(String rawPassword, String hashedPassword) {
        try {
            return Hashing.matches(rawPassword, hashedPassword);
        } catch (Exception e) {
            throw new PasswordCheckException(e);
        }
    }
}
