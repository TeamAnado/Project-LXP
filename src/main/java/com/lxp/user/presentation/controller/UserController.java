package com.lxp.user.presentation.controller;

import com.lxp.user.presentation.controller.request.UserFindPasswordRequest;
import com.lxp.user.presentation.controller.request.UserFindRequest;
import com.lxp.user.presentation.controller.request.UserLoginRequest;
import com.lxp.user.presentation.controller.request.UserSaveRequest;
import com.lxp.user.presentation.controller.request.UserUpdatePasswordRequest;
import com.lxp.user.presentation.controller.response.UserFindResponse;
import com.lxp.user.presentation.controller.response.UserResponse;
import com.lxp.user.presentation.controller.response.UserSaveResponse;
import com.lxp.user.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserSaveResponse register(UserSaveRequest request) {
        return userService.register(request.to());
    }

    /**
     * 비밀번호 찾는 기능 중 이메일이 있는지 확인
     *
     * @param email
     * @return
     */
    public UserResponse checkUserExistence(String email) {
        return UserResponse.from(userService.findByEmail(email));
    }

    public boolean resetPassword(UserFindPasswordRequest request) {
        return userService.resetPassword(request.to());
    }

    public boolean updatePassword(UserUpdatePasswordRequest request) {
        return userService.updatePassword(request.to());
    }

    public UserFindResponse myPage(UserFindRequest request) {
        return UserFindResponse.from(userService.findUser(request.to()));
    }

    public UserResponse login(UserLoginRequest request) {
        return UserResponse.from(userService.login(request.to()));
    }

}
