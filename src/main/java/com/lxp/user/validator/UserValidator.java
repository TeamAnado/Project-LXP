package com.lxp.user.validator;

import com.lxp.user.exception.InvalidEmailException;
import com.lxp.user.exception.InvalidNameException;
import com.lxp.user.exception.InvalidPasswordException;

import static com.lxp.support.StringUtils.isBlank;

public class UserValidator {
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int USERNAME_MAX_LENGTH = 16;
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static void validateUsername(String username) {
        if (isBlank(username) || invalidUsernameLength(username)) {
            throw new InvalidNameException();
        }
    }

    public static void validateEmail(String email) {
        if (isBlank(email) || !email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException();
        }
    }

    public static void validatePassword(String password) {
        if (isBlank(password) || !password.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordException();
        }
    }

    private static boolean invalidUsernameLength(String username) {
        int usernameLength = username.length();
        return usernameLength < USERNAME_MIN_LENGTH || usernameLength > USERNAME_MAX_LENGTH;
    }
}
