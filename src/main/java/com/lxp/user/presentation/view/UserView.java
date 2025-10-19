package com.lxp.user.presentation.view;

import com.lxp.global.context.SessionContext;
import com.lxp.global.exception.LXPException;
import com.lxp.global.exception.LXPExceptionHandler;
import com.lxp.support.StringUtils;
import com.lxp.user.exception.InvalidPasswordException;
import com.lxp.user.presentation.controller.UserController;
import com.lxp.user.presentation.controller.request.UserFindPasswordRequest;
import com.lxp.user.presentation.controller.request.UserFindRequest;
import com.lxp.user.presentation.controller.request.UserLoginRequest;
import com.lxp.user.presentation.controller.request.UserPasswordCheckRequest;
import com.lxp.user.presentation.controller.request.UserSaveRequest;
import com.lxp.user.presentation.controller.request.UserUpdateInfoRequest;
import com.lxp.user.presentation.controller.request.UserUpdatePasswordRequest;
import com.lxp.user.presentation.controller.response.UserFindResponse;
import com.lxp.user.presentation.controller.response.UserResponse;
import com.lxp.user.presentation.controller.response.UserSaveResponse;

import java.util.Arrays;
import java.util.Scanner;

import static com.lxp.global.exception.LXPExceptionHandler.handle;
import static com.lxp.support.StringUtils.isBlank;
import static com.lxp.support.StringUtils.isNull;

/**
 * 콘솔 애플리케이션의 사용자 인터페이스(View) 및 요청 처리(Handler) 역할을 수행하는 클래스입니다.
 * 사용자로부터 입력을 받고, 결과를 출력하며, UserController로 요청을 위임합니다.
 */
public class UserView {

    private final UserController userController;
    private final Scanner scanner;

    public UserView(UserController userController, Scanner scanner) {
        this.userController = userController;
        this.scanner = scanner;
    }

    /**
     * 앱 시작 시 표시되는 초기 인증 메뉴(로그인/회원가입/비밀번호 찾기)의 흐름을 제어합니다.
     * 사용자가 로그인을 성공하면 UserResponse와 함께 인증된 ID를 설정합니다.
     *
     * @return UserResponse 로그인 성공 시 ID를 포함하며, 뒤로가기 시 empty()를 반환
     */
    public UserResponse loginAndRegister() {
        while (true) {
            try {
                int n = Integer.parseInt(displayAndGetInput("1.로그인, 2.회원가입, 3. 비밀번호 찾기, 4. 뒤로가기: "));

                return switch (n) {
                    case 1 -> handleLoginAndSetId();
                    case 2 -> {
                        handleRegister();
                        yield handleLoginAndSetId();
                    }
                    case 3 -> {
                        if (!handlePasswordFind()) {
                            System.out.println("비밀번호 찾기 실패");
                            yield UserResponse.empty();
                        }
                        yield handleLoginAndSetId();
                    }
                    case 4 -> UserResponse.empty();
                    default -> throw new LXPException("Unexpected value: " + n);
                };
            } catch (Exception e) {
                handle(e);
            }
        }
    }

    /**
     * 로그인 성공 후 표시되는 주 메뉴(로그아웃/마이페이지)의 흐름을 제어합니다.
     */
    public void showLoginUser() {
        while (true) {
            try {
                int n = Integer.parseInt(displayAndGetInput("1.로그아웃, 2.마이페이지"));

                if (n == 1) {
                    logout();
                    return;
                } else if (n == 2) {
                    showMyPage();
                } else {
                    System.out.println("잘못된 입력입니다. 메뉴를 다시 선택해주세요.");
                }
            } catch (Exception e) {
                handle(e);
            }
        }
    }

    /**
     * 마이페이지 정보를 출력하고, 관련된 기능 메뉴 실행 흐름을 시작합니다.
     */
    public void showMyPage() {
        if (isLoggedOutAndWarn()) return;
        printMyPage();
        runMyPageFunction();
    }

    /**
     * 마이페이지 내부 기능(개인정보 수정, 비밀번호 변경 등)의 흐름을 제어합니다.
     */
    public void runMyPageFunction() {
        if (isLoggedOutAndWarn()) return;
        while (true) {
            try {
                String answer = displayAndGetInput("1.개인정보 수정, 2. 비밀번호 변경, 3.새로고침, 4.뒤로가기: ");
                int n = Integer.parseInt(answer);
                if (n == 1) {
                    handleUserUpdate();
                } else if (n == 2) {
                    if (!handlePasswordUpdate()) {
                        System.out.println("비밀번호 변경에 실패하였습니다.");
                    }
                } else if (n == 3) {
                    printMyPage();
                } else if (n == 4) {
                    break;
                } else {
                    System.out.println("잘못된 입력입니다. 메뉴를 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            } catch (LXPException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                LXPExceptionHandler.handle(e);
            }
        }
    }

    //-------------------------------------------------------------------

    /**
     * 사용자에게 메뉴 메시지를 출력하고 콘솔 입력을 받습니다.
     * 입력이 비어있으면 LXPException을 발생시킵니다.
     *
     * @param message 사용자에게 표시할 메뉴 프롬프트 메시지
     * @return String 사용자가 입력한 문자열
     * @throws LXPException 입력이 공백일 경우
     */
    public String displayAndGetInput(String message) {
        System.out.print(message);
        String answer = scanner.nextLine();
        if (isBlank(answer)) {
            throw new LXPException("잘못된 입력입니다.");
        }
        return answer;
    }

    /**
     * 사용자로부터 이름, 이메일, 비밀번호를 입력받아 회원가입을 처리하고 결과를 출력합니다.
     */
    public void handleRegister() {
        System.out.println("=== 회원가입 ===");

        String name = displayAndGetInput("이름: ");
        String email = displayAndGetInput("이메일: ");
        String password = displayAndGetInput("비밀번호: ");

        UserSaveResponse register = userController.register(new UserSaveRequest(name, email, password));
        System.out.println(register.name() + "(" + register.email() + ")님 가입을 환영합니다.");
    }

    /**
     * 로그인 처리 후, 반환된 사용자 ID를 현재 로그인 상태(currentUserId)에 설정합니다.
     *
     * @return UserResponse 로그인 성공 정보
     */
    public UserResponse handleLoginAndSetId() {
        UserResponse userResponse = processLogin();
        if (userResponse.isEmpty()) {
            System.out.println("로그인 정보가 올바르지 않습니다.");
            return UserResponse.empty();
        }
        SessionContext.getInstance().setUserId(userResponse.id());
        return userResponse;
    }

    /**
     * 현재 로그인 상태를 해제하고 사용자 ID를 초기화합니다.
     */
    public void logout() {
        SessionContext.getInstance().clear();
    }

    /**
     * 이메일을 입력받아 사용자 존재 여부를 확인하고, 새 비밀번호를 입력받아 재설정합니다.
     *
     * @return boolean 비밀번호 재설정 성공 여부
     */
    public boolean handlePasswordFind() {
        System.out.println("=== 비밀번호 찾기 ===");
        String email = displayAndGetInput("아이디(이메일): ");

        UserResponse userResponse = userController.getUserByEmailResponse(email);
        if (userResponse.isEmpty()) {
            System.out.println("잘못된 이메일입니다.");
            return false;
        }

        String newPassword = displayAndGetInput("새 비밀번호: ");
        return userController.resetPassword(new UserFindPasswordRequest(userResponse.id(), newPassword));
    }

    /**
     * 기존 비밀번호와 새 비밀번호를 입력받아 현재 로그인된 사용자의 비밀번호를 업데이트합니다.
     *
     * @return boolean 비밀번호 업데이트 성공 여부
     */
    public boolean handlePasswordUpdate() {
        if (isLoggedOutAndWarn()) return false;
        System.out.println("=== 비밀번호 변경 ===");

        String oldPassword = displayAndGetInput("기존 비밀번호: ");
        String newPassword = displayAndGetInput("새 비밀번호: ");
        checkPassword(oldPassword, newPassword);

        if (newPassword.length() < 8) {
            System.out.println("비밀번호는 8자 이상이어야 합니다.");
            return false;
        }
        if (newPassword.equals(oldPassword)) {
            System.out.println("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
            return false;
        }

        String confirm = displayAndGetInput("새 비밀번호 확인: ");
        if (!newPassword.equals(confirm)) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return false;
        }

        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(getUserId(), oldPassword, newPassword);
        return userController.updatePassword(request);
    }

    public void handleUserUpdate() {
        if (isLoggedOutAndWarn()) return;
        System.out.println("=== 유저 정보 변경 ===");

        String password = displayAndGetInput("비밀번호: ");
        boolean validPassword = userController.isValidPassword(new UserPasswordCheckRequest(getUserId(), password));
        if (!validPassword) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return;
        }

        String newName = displayAndGetInput("새로운 이름:");

        UserUpdateInfoRequest request = new UserUpdateInfoRequest(getUserId(), newName);
        boolean flag = userController.updateUsername(request);
        if (!flag) {
            System.out.println("실패하였습니다");
        } else {
            System.out.println("이름이 변경되었습니다.");
        }
    }

    /**
     * 이메일과 비밀번호를 입력받아 로그인을 시도하고 결과를 반환합니다.
     *
     * @return UserResponse 로그인 성공 시 ID를 포함
     */
    private UserResponse processLogin() {
        System.out.println("=== 로그인 ===");
        String email = displayAndGetInput("아이디(이메일): ");
        String password = displayAndGetInput("비밀번호: ");

        return userController.login(new UserLoginRequest(email, password));
    }

    /**
     * 현재 로그인된 사용자(currentUserId)의 상세 정보를 조회하여 콘솔에 출력합니다.
     */
    private void printMyPage() {
        System.out.println("=== 마이 페이지 ===");
        UserFindRequest request = new UserFindRequest(getUserId());
        UserFindResponse response = userController.myPage(request);
        System.out.println(response.toString());
    }

    private long getUserId() {
        return SessionContext.getInstance().getUserId();
    }

    private boolean isLoggedOutAndWarn() {
        if (!SessionContext.getInstance().isLoggedIn()) {
            System.out.println("로그인이 필요합니다.");
            return true;
        }
        return false;
    }

    private void checkPassword(String... passwords) {
        if (isNull(passwords) || passwords.length == 0) {
            throw new InvalidPasswordException("비밀번호는 필수 입력 항목입니다.");
        }
        boolean isAnyPasswordBlank = Arrays.stream(passwords)
            .anyMatch(StringUtils::isBlank);

        if (isAnyPasswordBlank) {
            throw new InvalidPasswordException("이전 비밀번호와 새 비밀번호는 필수입니다. 공백이 될 수 없습니다.");
        }
    }
}
