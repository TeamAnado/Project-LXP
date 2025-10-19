package com.lxp.enrollment.presentation.view;

import com.lxp.enrollment.presentation.controller.EnrollmentController;
import com.lxp.enrollment.presentation.controller.request.EnrollmentCheckRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentCompleteRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentDeleteRequest;
import com.lxp.enrollment.presentation.controller.request.EnrollmentSaveRequest;
import com.lxp.enrollment.presentation.controller.response.EnrollmentCheckResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentCompleteResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentDeleteResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentFindResponse;
import com.lxp.enrollment.presentation.controller.response.EnrollmentSaveResponse;
import com.lxp.enrollment.service.dto.EnrollmentCourseDto;

import java.util.List;
import java.util.Scanner;


public class EnrollmentView {

    private static final long USER_ID = 1L;
    private final EnrollmentController controller;
    private final Scanner scanner;

    public EnrollmentView(EnrollmentController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("===== 수강 관리 메뉴 =====");
                System.out.println("1. 수강 신청");
                System.out.println("2. 수강 취소");
                System.out.println("3. 수강 완료 처리");
                System.out.println("4. 내 강좌 목록 보기");
                System.out.println("5. (강의) 수강 여부 확인");
                System.out.println("0. 뒤로가기");
                System.out.print("선택: ");

                int n = Integer.parseInt(scanner.nextLine().trim());

                switch (n) {
                    case 1 -> {
                        long courseId = askLong("수강 신청할 강좌 ID: ");
                        EnrollmentSaveResponse response =
                                controller.save(new EnrollmentSaveRequest(courseId), USER_ID);
                        System.out.println(response.message());
                    }
                    case 2 -> {
                        long courseId = askLong("취소할 강좌 ID: ");
                        EnrollmentDeleteResponse response =
                                controller.delete(new EnrollmentDeleteRequest(courseId), USER_ID);
                        System.out.println(response.message());
                    }
                    case 3 -> {
                        long courseId = askLong("완료 처리할 강좌 ID: ");
                        EnrollmentCompleteResponse response =
                                controller.complete(new EnrollmentCompleteRequest(courseId), USER_ID);
                        System.out.println(response.message());
                    }
                    case 4 -> {
                        EnrollmentFindResponse response = controller.findCoursesByUser(USER_ID);
                        List<EnrollmentCourseDto> courses = response.courses();

                        if (courses == null || courses.isEmpty()) {
                            System.out.println("현재 수강 중이거나 완료된 강좌가 없습니다.");
                        } else {
                            System.out.println("=== 나의 강좌 목록 (" + courses.size() + "개) ===");
                            for (EnrollmentCourseDto c : courses) {
                                String stateLabel = switch (c.state()) {
                                    case 1 -> "수강중";
                                    case 3 -> "수강완료";
                                    default -> "취소됨";
                                };
                                System.out.printf("%d. %s [%s]%n", c.courseId(), c.title(), stateLabel);
                            }
                        }
                    }
                    case 5 -> {
                        long lectureId = askLong("확인할 강의 ID: ");
                        EnrollmentCheckResponse response =
                                controller.isUserEnrolled(new EnrollmentCheckRequest(lectureId), USER_ID);
                        System.out.println(response.enrolled() ? "수강 중입니다." : "수강 중이 아닙니다.");
                    }
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다.");
                }

            } catch (Exception e) {
            }
        }
    }

    private long askLong(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("숫자를 입력해주세요: ");
            }
        }
    }
}
