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

    private static long userId;

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

                return switch (n) {
                    case 1 -> {
                        UserResponse userResponse = processLogin();
                        userId = userResponse.id();
                        yield userResponse;
                    }
                    case 3 -> {
                        findPassword(userId);
                        yield new UserResponse(userId);
                    }
                    case 4 -> UserResponse.empty();
                    default -> throw new LXPException("Unexpected value: " + n);
                };
            } catch (Exception e) {
                LXPExceptionHandler.handle(e);
            }
        }
    }

    public String displayMenuAndGetInput() {
        System.out.print("1.로그인, 2.회원가입, 3. 비밀번호 찾기, 4. 뒤로가기: ");
        return scanner.nextLine();
    }

    public boolean findPassword(Long id) {
        System.out.println("=== 비밀번호 찾기 ===");
        System.out.print("아이디(이메일): ");
        String email = scanner.nextLine();

        if (!userController.checkUserExistence(email)) {
            System.out.println("잘못된 이메일입니다.");
            return false;
        }

        System.out.print("새 비밀번호: ");
        String newPassword = scanner.nextLine();
        userController.resetPassword(new UserFindPasswordRequest(id, newPassword));
        return true;
    }

    public UserResponse processLogin() {
        System.out.println("=== 로그인 ===");
        System.out.print("아이디(이메일): ");
        String id = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        return userController.login(new UserLoginRequest(id, password));
    }
}
