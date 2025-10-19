package com.lxp.course.presentation.view;

import com.lxp.course.model.enums.Category;
import com.lxp.course.presentation.controller.CourseController;
import com.lxp.course.presentation.controller.request.CourseCreateRequest;
import com.lxp.course.presentation.controller.request.CourseUpdateRequest;
import com.lxp.course.presentation.controller.response.CourseCreateResponse;
import com.lxp.course.presentation.controller.response.CourseDetailResponse;
import com.lxp.course.presentation.controller.response.CourseResponse;
import com.lxp.global.exception.LXPException;
import com.lxp.global.exception.LXPExceptionHandler;
import com.lxp.global.util.InputUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseView {

    private final CourseController courseController;
    private final InputUtil inputUtil;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CourseView(CourseController courseController, InputUtil inputUtil) {
        this.courseController = courseController;
        this.inputUtil = inputUtil;
    }

    public void showCourseMenu() {
        while (true) {
            try {
                displayMainMenu();
                int choice = inputUtil.getInt("> ", "유효한 숫자를 입력해주세요.");

                switch (choice) {
                    case 1 -> createCourse();
                    case 2 -> showAllCourses();
                    case 3 -> showCourseDetail();
                    case 4 -> searchCourses();
                    case 5 -> updateCourse();
                    case 6 -> deleteCourse();
                    case 0 -> {
                        System.out.println("강의 관리를 종료합니다.");
                        return;
                    }
                    default -> System.out.println("올바른 메뉴 번호를 선택해주세요.");
                }
                System.out.println();
            } catch (Exception e) {
                LXPExceptionHandler.handle(e);
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("=== 강의 관리 ===");
        System.out.println("1. 강의 생성");
        System.out.println("2. 전체 강의 조회");
        System.out.println("3. 강의 상세 조회");
        System.out.println("4. 강의 검색");
        System.out.println("5. 강의 수정");
        System.out.println("6. 강의 삭제");
        System.out.println("0. 뒤로가기");
    }

    private void createCourse() {
        System.out.println("=== 강의 생성 ===");
        
        String title = inputUtil.getString("강의 제목: ", "강의 제목을 입력해주세요.");
        String description = inputUtil.getString("강의 설명: ", "강의 설명을 입력해주세요.");
        long instructorId = inputUtil.getLong("강사 ID: ", "유효한 강사 ID를 입력해주세요.");
        
        Category category = selectCategory();
        
        CourseCreateRequest request = new CourseCreateRequest(title, description, instructorId, category);
        CourseCreateResponse response = courseController.createCourse(request);
        
        System.out.println(response.message() + " (ID: " + response.id() + ")");
    }

    private void showAllCourses() {
        System.out.println("=== 전체 강의 목록 ===");
        
        List<CourseResponse> courses = courseController.findAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("등록된 강의가 없습니다.");
            return;
        }
        
        displayCourseList(courses);
    }

    private void showCourseDetail() {
        System.out.println("=== 강의 상세 조회 ===");
        
        long courseId = inputUtil.getLong("강의 ID: ", "유효한 강의 ID를 입력해주세요.");
        
        try {
            CourseDetailResponse course = courseController.findCourseById(courseId);
            displayCourseDetail(course);
        } catch (Exception e) {
            System.out.println("강의를 찾을 수 없습니다: " + e.getMessage());
        }
    }

    private void searchCourses() {
        System.out.println("=== 강의 검색 ===");
        System.out.println("1. 제목으로 검색");
        System.out.println("2. 카테고리로 검색");
        System.out.println("3. 강사 ID로 검색");
        
        int choice = inputUtil.getInt("> ", "유효한 숫자를 입력해주세요.");
        
        List<CourseResponse> courses;
        
        switch (choice) {
            case 1 -> {
                String title = inputUtil.getString("검색할 제목: ", "제목을 입력해주세요.");
                courses = courseController.searchCoursesByTitle(title);
            }
            case 2 -> {
                Category category = selectCategory();
                courses = courseController.searchCoursesByCategory(category);
            }
            case 3 -> {
                long instructorId = inputUtil.getLong("강사 ID: ", "유효한 강사 ID를 입력해주세요.");
                courses = courseController.searchCoursesByInstructor(instructorId);
            }
            default -> {
                System.out.println("올바른 검색 옵션을 선택해주세요.");
                return;
            }
        }
        
        if (courses.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            displayCourseList(courses);
        }
    }

    private void updateCourse() {
        System.out.println("=== 강의 수정 ===");
        
        long courseId = inputUtil.getLong("수정할 강의 ID: ", "유효한 강의 ID를 입력해주세요.");
        
        try {
            // 현재 강의 정보 표시
            CourseDetailResponse current = courseController.findCourseById(courseId);
            System.out.println("현재 강의 정보:");
            displayCourseDetail(current);
            
            System.out.println("\n새 정보를 입력해주세요:");
            String title = inputUtil.getString("강의 제목: ", "강의 제목을 입력해주세요.");
            String description = inputUtil.getString("강의 설명: ", "강의 설명을 입력해주세요.");
            Category category = selectCategory();
            
            CourseUpdateRequest request = new CourseUpdateRequest(courseId, title, description, category);
            
            if (courseController.updateCourse(request)) {
                System.out.println("강의가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("강의 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            System.out.println("강의 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("=== 강의 삭제 ===");
        
        long courseId = inputUtil.getLong("삭제할 강의 ID: ", "유효한 강의 ID를 입력해주세요.");
        
        try {
            // 강의 정보 표시
            CourseDetailResponse course = courseController.findCourseById(courseId);
            System.out.println("삭제할 강의 정보:");
            displayCourseDetail(course);
            
            boolean confirmed = inputUtil.getConfirmation("정말로 이 강의를 삭제하시겠습니까?");
            
            if (confirmed) {
                if (courseController.deleteCourse(courseId)) {
                    System.out.println("강의가 성공적으로 삭제되었습니다.");
                } else {
                    System.out.println("강의 삭제에 실패했습니다.");
                }
            } else {
                System.out.println("강의 삭제를 취소했습니다.");
            }
        } catch (Exception e) {
            System.out.println("강의 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private Category selectCategory() {
        System.out.println("카테고리를 선택해주세요:");
        System.out.println("1. DEVELOPMENT");
        System.out.println("2. DESIGN");
        System.out.println("3. MARKETING");
        System.out.println("4. BUSINESS");
        
        int choice = inputUtil.getInt("> ", "1-4 사이의 숫자를 입력해주세요", 1, 4);
        
        return switch (choice) {
            case 1 -> Category.DEVELOPMENT;
            case 2 -> Category.DESIGN;
            case 3 -> Category.MARKETING;
            case 4 -> Category.BUSINESS;
            default -> throw new LXPException("올바른 카테고리를 선택해주세요."); // This should never happen with range validation
        };
    }

    private void displayCourseList(List<CourseResponse> courses) {
        System.out.printf("%-5s %-30s %-15s %-10s%n", "ID", "제목", "카테고리", "강사ID");
        System.out.println("-".repeat(65));
        
        for (CourseResponse course : courses) {
            System.out.printf("%-5d %-30s %-15s %-10d%n",
                    course.id(),
                    truncateString(course.title(), 28),
                    course.category(),
                    course.instructorId());
        }
    }

    private void displayCourseDetail(CourseDetailResponse course) {
        System.out.println("강의 ID: " + course.id());
        System.out.println("제목: " + course.title());
        System.out.println("설명: " + course.description());
        System.out.println("카테고리: " + course.category());
        System.out.println("강사 ID: " + course.instructorId());
        System.out.println("생성일시: " + course.dateCreated().format(dateFormatter));
    }

    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 2) + "..";
    }

}
