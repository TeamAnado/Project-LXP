package com.lxp.user.presentation.view;

import com.lxp.exception.LXPException;
import com.lxp.exception.LXPExceptionHandler;
import com.lxp.user.presentation.controller.UserController;
import com.lxp.user.presentation.controller.request.UserFindPasswordRequest;
import com.lxp.user.presentation.controller.request.UserLoginRequest;
import com.lxp.user.presentation.controller.request.UserSaveRequest;
import com.lxp.user.presentation.controller.request.UserUpdatePasswordRequest;
import com.lxp.user.presentation.controller.response.UserResponse;
import com.lxp.user.presentation.controller.response.UserSaveResponse;

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
                    case 1 -> handleLoginAndSetId();
                    case 2 -> {
                        register();
                        yield handleLoginAndSetId();
                    }
                    case 3 -> {
                        findPassword();
                        yield handleLoginAndSetId();
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

    public void register() {
        System.out.println("=== 회원가입 ===");
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("이메일: ");
        String email = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        UserSaveResponse register = userController.register(new UserSaveRequest(name, email, password));
        System.out.println(register.name() + "(" + register.email() + ")님 가입을 환영합니다.");
    }

    public boolean findPassword() {
        System.out.println("=== 비밀번호 찾기 ===");
        System.out.print("아이디(이메일): ");
        String email = scanner.nextLine();

        UserResponse userResponse = userController.checkUserExistence(email);
        if (userResponse.isEmpty()) {
            System.out.println("잘못된 이메일입니다.");
            return false;
        }

        System.out.print("새 비밀번호: ");
        String newPassword = scanner.nextLine();
        userController.resetPassword(new UserFindPasswordRequest(userResponse.id(), newPassword));
        return true;
    }

    public boolean updatePassword() {
        System.out.println("=== 비밀번호 변경 ===");

        System.out.print("기존 비밀번호: ");
        String oldPassword = scanner.nextLine();

        System.out.print("새 비밀번호: ");
        String newPassword = scanner.nextLine();

        return userController.updatePassword(new UserUpdatePasswordRequest(userId, oldPassword, newPassword));
    }

    public UserResponse processLogin() {
        System.out.println("=== 로그인 ===");
        System.out.print("아이디(이메일): ");
        String id = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        return userController.login(new UserLoginRequest(id, password));
    }

    private UserResponse handleLoginAndSetId() {
        UserResponse userResponse = processLogin();
        userId = userResponse.id();
        return userResponse;
    }
}
