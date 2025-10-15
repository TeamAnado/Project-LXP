package com.lxp.user.service;

import com.lxp.user.User;
import com.lxp.user.dao.UserDao;
import com.lxp.user.dto.UserSaveRequest;
import com.lxp.user.dto.UserSaveResponse;
import com.lxp.user.validator.PasswordEncoder;

import java.sql.SQLException;

public class UserService {
    private final PasswordEncoder encoder;
    private final UserDao userDao;

    public UserService(PasswordEncoder encoder, UserDao userDao) {
        this.encoder = encoder;
        this.userDao = userDao;
    }

    public UserSaveResponse register(UserSaveRequest request) throws SQLException {
        String hashedPassword = encoder.encode(request.rawPassword());
        User user = request.to(hashedPassword);
        userDao.save(user);
        return UserSaveResponse.to(user);
    }
}
