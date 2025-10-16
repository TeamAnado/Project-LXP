package com.lxp.user.presentation.controller;

import com.lxp.user.presentation.controller.request.UserFindPasswordRequest;
import com.lxp.user.presentation.controller.request.UserLoginRequest;
import com.lxp.user.presentation.controller.request.UserUpdatePasswordRequest;
import com.lxp.user.presentation.controller.response.UserResponse;
import com.lxp.user.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 비밀번호 찾는 기능 중 assert
     *
     * @param email
     * @return
     */
    public UserResponse checkUserExistence(String email) {
        return userService.isExistUser(email).toResponse();
    }

    public boolean resetPassword(UserFindPasswordRequest request) {
        return userService.resetPassword(request.to());
    }

    public boolean updatePassword(UserUpdatePasswordRequest request) {
        return userService.updatePassword(request.to());
    }

    public UserResponse login(UserLoginRequest request) {
        return userService.login(request.to()).toResponse();
    }

}
