package com.lxp;

import com.lxp.global.config.DBConfig;
import com.lxp.global.exception.LXPExceptionHandler;
import com.lxp.config.DBConfig;
import com.lxp.course.dao.CourseDao;
import com.lxp.exception.LXPExceptionHandler;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.presentation.controller.LectureController;
import com.lxp.lecture.presentation.view.LectureView;
import com.lxp.lecture.service.LectureService;
import java.sql.Connection;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             DBConfig dbConfig = DBConfig.getInstance();
             Connection connection = dbConfig.getConnection()) {

            CourseDao courseDao = new CourseDao(connection);
            LectureDao lectureDao = new LectureDao(connection);
            LectureService lectureService = new LectureService(lectureDao, courseDao);
            LectureView lectureView = new LectureView(scanner);
            LectureController lectureController = new LectureController(lectureService, lectureView);

            while (true) {
                System.out.println("\n--- 메인 메뉴 ---");
                System.out.println("1. 강의 생성");
                System.out.println("2. 강의 수정");
                System.out.println("0. 종료");
                System.out.print("선택: ");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> lectureController.createLecture();
                    case "2" -> lectureController.updateLecture();
                    case "0" -> {
                        System.out.println("애플리케이션을 종료합니다.");
                        return;
                    }
                    default -> System.err.println("잘못된 입력입니다. 다시 시도하세요.");
                }
            }

        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }
}
