package com.lxp.user.service;

import com.lxp.user.dao.UserDao;
import com.lxp.user.dao.vo.UserAuthInfo;
import com.lxp.user.dao.vo.UserInfo;
import com.lxp.user.exception.InvalidEmailException;
import com.lxp.user.exception.UserAlreadyExistsException;
import com.lxp.user.exception.UserNotFoundException;
import com.lxp.user.model.User;
import com.lxp.user.presentation.controller.response.UserSaveResponse;
import com.lxp.user.security.PasswordEncoder;
import com.lxp.user.service.dto.UserFindDto;
import com.lxp.user.service.dto.UserFindPasswordDto;
import com.lxp.user.service.dto.UserLoginDto;
import com.lxp.user.service.dto.UserSaveDto;
import com.lxp.user.service.dto.UserUpdateInfoDto;
import com.lxp.user.service.dto.UserUpdatePasswordDto;
import com.lxp.user.service.validator.UserValidator;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserService {

    private final PasswordEncoder encoder;
    private final UserValidator validator;
    private final UserDao userDao;

    public UserService(PasswordEncoder encoder, UserValidator validator, UserDao userDao) {
        this.encoder = encoder;
        this.validator = validator;
        this.userDao = userDao;
    }

    public UserSaveResponse register(UserSaveDto dto) {
        Objects.requireNonNull(dto, "request must not be null");

        if (userDao.existByEmail(dto.email())) {
            throw new UserAlreadyExistsException();
        }
        validateRegister(dto);

        String hashedPassword = encoder.encode(dto.rawPassword());
        User user = dto.to(hashedPassword);
        userDao.save(user);

        return UserSaveResponse.to(user);
    }

    public UserAuthInfo login(UserLoginDto dto) {
        Objects.requireNonNull(dto, "request must not be null");

        UserAuthInfo info = findByEmail(dto.email());
        validator.authenticatePassword(dto.password(), info.password());

        return info;
    }

    public UserAuthInfo findByEmail(String email) {
        try {
            validator.validateEmail(email);
            return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
        } catch (InvalidEmailException | UserNotFoundException e) {
            throw new UserNotFoundException("이메일 또는 비밀번호가 틀렸습니다.");
        }
    }

    public UserInfo findById(Long id) {
        return userDao.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public UserInfo findUser(UserFindDto dto) {
        return findById(dto.id());
    }

    public boolean updateUserInfo(UserUpdateInfoDto dto) {
        if (userDao.existById(dto.id())) {
            throw new UserNotFoundException();
        }
        validator.validateUsername(dto.name());
        return userDao.updateUser(dto.id(), LocalDateTime.now(), dto.name());
    }

    public boolean updatePassword(UserUpdatePasswordDto dto) {
        Objects.requireNonNull(dto, "request must not be null");

        UserAuthInfo userAuthInfo = userDao.findByIdWithPassword(dto.id())
            .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        validator.authenticatePassword(dto.oldPassword(), userAuthInfo.password());
        validator.validatePassword(dto.newPassword());

        String hashedPassword = encoder.encode(dto.newPassword());
        return userDao.updatePassword(dto.id(), LocalDateTime.now(), hashedPassword);
    }

    public boolean resetPassword(UserFindPasswordDto dto) {
        if (!userDao.existById(dto.id())) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다.");
        }
        validator.validatePassword(dto.newPassword());

        String hashedPassword = encoder.encode(dto.newPassword());
        return userDao.updatePassword(dto.id(), LocalDateTime.now(), hashedPassword);
    }

    private void validateRegister(UserSaveDto dto) {
        validator.validateEmail(dto.email());
        validator.validateUsername(dto.name());
        validator.validatePassword(dto.rawPassword());
    }

}
