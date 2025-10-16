package com.lxp.user.service.validator;

import com.lxp.user.exception.InvalidEmailException;
import com.lxp.user.exception.InvalidNameException;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.security.PasswordEncoder;

import static com.lxp.support.StringUtils.isBlank;

public class UserValidator {
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int USERNAME_MAX_LENGTH = 16;
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private final PasswordEncoder encoder;

    public UserValidator(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public void validateUsername(String username) {
        if (isBlank(username) || invalidUsernameLength(username)) {
            throw new InvalidNameException();
        }
    }

    public void validateEmail(String email) {
        if (isBlank(email) || !email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException();
        }
    }

    public void validatePassword(String password) {
        if (isBlank(password) || !password.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordException();
        }
    }

    public void authenticatePassword(String rawPassword, String hashedPassword) {
        if (!encoder.check(rawPassword, hashedPassword)) {
            throw new InvalidPasswordException("이메일 또는 비밀번호가 틀렸습니다.");
        }
    }

    private boolean invalidUsernameLength(String username) {
        int usernameLength = username.length();
        return usernameLength < USERNAME_MIN_LENGTH || usernameLength > USERNAME_MAX_LENGTH;
    }

}
