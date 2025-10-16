package com.lxp.user.presentation.view;

import com.lxp.exception.LXPException;
import com.lxp.exception.LXPExceptionHandler;
import com.lxp.user.presentation.controller.UserController;
import com.lxp.user.presentation.controller.request.UserFindPasswordRequest;
import com.lxp.user.presentation.controller.request.UserLoginRequest;
import com.lxp.user.presentation.controller.response.UserResponse;

import java.util.Scanner;

import static com.lxp.support.StringUtils.isBlank;

public class UserView {

    private final UserController userController;
    private final Scanner scanner;

    public UserView(UserController userController, Scanner scanner) {
        this.userController = userController;
        this.scanner = scanner;
    }

    public UserResponse login() {
        while (true) {
            try {
                String answer = displayMenuAndGetInput();
                if (isBlank(answer)) {
                    throw new LXPException("잘못된 입력입니다.");
                }

                int n = Integer.parseInt(answer);
                if (n == 1) {
                    processLogin();
                } else if (n == 4) {
                    return UserResponse.empty();
                }
            } catch (Exception e) {
                LXPExceptionHandler.handle(e);
            }
        }
    }

    public String displayMenuAndGetInput() {
        System.out.print("1.로그인, 2.회원가입, 3. 비밀번호 찾기, 4. 뒤로가기: ");
        return scanner.nextLine();
    }

    public boolean findPassword() {
        System.out.print("이메일: ");
        String email = scanner.nextLine();

        Long id = userController.checkUserExistence(email).id();
        if (id != null) {
            System.out.println("잘못된 이메일입니다.");
            return false;
        }

        System.out.print("새 비밀번호: ");
        String newPassword = scanner.nextLine();
        userController.resetPassword(new UserFindPasswordRequest(id, newPassword));
        return true;
    }

    public UserResponse processLogin() {
        System.out.print("아이디: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        return userController.login(new UserLoginRequest(id, password));
    }
}
