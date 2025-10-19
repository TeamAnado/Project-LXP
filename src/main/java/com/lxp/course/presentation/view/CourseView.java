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
import com.lxp.global.support.InputUtil;
import com.lxp.course.model.Session;

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


    // 1. 강좌 목록
    // 2. 강좌 등록 (로그인 되어있을때만 표시)
    // 3. 로그아웃 (로그인 되어있을때만 표시)
    // 2. 회원 가입 (로그아웃 되어있을때만 표시)
    // 3. 로그인 (로그아웃 되어있을때만 표시)
    // ----------
    // q. 끝내기

        // 강좌 목록
        // == 강좌 목록 ==
        // 1. {course 1}
        // 2. {course 2}
        // 3. {course 3}
        // ...
        // ----------
        // a. 강좌 검색
        // b. 강좌 등록
        // q. 뒤로 가기

            // 강좌 검색 선택시
            // 입력받은 텍스트 기반으로 제목 검색하여 목록 보여주기
            // 상위 메뉴인 강좌 목록 화면 재활용

            // 강좌 등록 선택시
            // course.title
            // course.description
            // course.category 선택 화면으로
                // category 선택 화면
                // 1. {category 1}
                // 2. {category 2}
                // 3. {category 3}
                // ...
                // (취소 메뉴 없음 카테고리는 필수 항목)
            // instructor id는 사용자에게 보여지지 않지만 현재 로그인된 user_id가 instructor_id 값으로 입력됨
            // 순서대로 입력 후 저장

            // 개별 강좌 선택시
            // == {course.title} ==
            // == {course.description}
            // ----------
            // 1. 강의1
            // 2. 강의2
            // 3. 강의3
            // ...
            // ----------
            // a. 수강 신청하기 (수강중이 아닐때만 표시) -> enrollment 쪽 화면으로 넘어감 (구현 X)
            // b. 강좌 관리 (내가 이 강좌의 instructor 일 때만 표시)
            // q. 뒤로 가기

                // 강좌 관리 메뉴
                // == {course.title} ==
                // 1. 강좌 정보 수정하기
                // 2. 강좌 삭제하기
                // 3. 강의 등록하기 -> lecture 쪽 화면으로 넘어감 (구현 X)
                // ----------
                // q. 뒤로 가기

    /**
     * 강좌 목록 화면
     * 전체 강좌 목록을 보여주고 검색, 등록, 상세보기 옵션 제공
     */
    public void showCourseList() {
        try {
            System.out.println("=== 강좌 목록 ===");
            
            List<CourseResponse> courses = courseController.findAllCourses();
            
            if (courses.isEmpty()) {
                System.out.println("등록된 강좌가 없습니다.");
            } else {
                displayCourseList(courses);
            }
            
            System.out.println("----------");
            System.out.println("a. 강좌 검색");
            System.out.println("b. 강좌 등록");
            System.out.println("q. 뒤로 가기");
            
            if (!courses.isEmpty()) {
                System.out.println();
                System.out.println("강좌를 선택하려면 번호를 입력하세요 (1-" + courses.size() + ")");
            }
        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }
    
    /**
     * 검색된 강좌 목록 화면
     */
    public void showSearchResults(String searchTerm) {
        try {
            System.out.println("=== 검색 결과: " + searchTerm + " ===");
            
            List<CourseResponse> courses = courseController.searchCoursesByTitle(searchTerm);
            
            if (courses.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
            } else {
                displayCourseList(courses);
            }
            
            System.out.println("----------");
            System.out.println("a. 강좌 검색");
            System.out.println("b. 강좌 등록");
            System.out.println("q. 뒤로 가기");
            
            if (!courses.isEmpty()) {
                System.out.println();
                System.out.println("강좌를 선택하려면 번호를 입력하세요 (1-" + courses.size() + ")");
            }
        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }
    
    /**
     * 카테고리별 검색 결과 화면
     */
    public void showCategorySearchResults(Category category) {
        try {
            System.out.println("=== 카테고리 검색 결과: " + category + " ===");
            
            List<CourseResponse> courses = courseController.searchCoursesByCategory(category);
            
            if (courses.isEmpty()) {
                System.out.println("해당 카테고리에 강좌가 없습니다.");
            } else {
                displayCourseList(courses);
            }
            
            System.out.println("----------");
            System.out.println("a. 강좌 검색");
            System.out.println("b. 강좌 등록");
            System.out.println("q. 뒤로 가기");
            
            if (!courses.isEmpty()) {
                System.out.println();
                System.out.println("강좌를 선택하려면 번호를 입력하세요 (1-" + courses.size() + ")");
            }
        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }

    /**
     * 강좌 등록 화면
     */
    public void showCourseCreation() {
        try {
            System.out.println("=== 강좌 등록 ===");
            
            String title = inputUtil.getString("강좌 제목: ", "강좌 제목을 입력해주세요.");
            String description = inputUtil.getString("강좌 설명: ", "강좌 설명을 입력해주세요.");
            long instructorId = Session.getUserId(); // 현재 로그인한 사용자 ID를 강사 ID로 사용
            
            System.out.println("\n카테고리를 선택해주세요:");
            showCategorySelection();
            Category category = selectCategory();
            
            CourseCreateRequest request = new CourseCreateRequest(title, description, instructorId, category);
            CourseCreateResponse response = courseController.createCourse(request);
            
            System.out.println(response.message());
        } catch (Exception e) {
            System.out.println("강좌 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 카테고리 선택 화면
     */
    public void showCategorySelection() {
        System.out.println("1. DEVELOPMENT");
        System.out.println("2. DESIGN");
        System.out.println("3. MARKETING");
        System.out.println("4. BUSINESS");
    }

    /**
     * 강좌 검색 화면
     */
    public void showSearchOptions() {
        try {
            System.out.println("=== 강좌 검색 ===");
            System.out.println("1. 제목으로 검색");
            System.out.println("2. 카테고리로 검색");
            System.out.println("0. 뒤로 가기");
        } catch (Exception e) {
            LXPExceptionHandler.handle(e);
        }
    }

    /**
     * 강좌 관리 메뉴 화면
     */
    public void showCourseManagement(long courseId) {
        try {
            CourseDetailResponse course = courseController.findCourseById(courseId);
            
            // 권한 확인
            if (course.instructorId() != Session.getUserId()) {
                System.out.println("권한이 없습니다. 자신이 생성한 강좌만 관리할 수 있습니다.");
                return;
            }
            
            System.out.println("=== " + course.title() + " 관리 ===");
            System.out.println("1. 강좌 정보 수정하기");
            System.out.println("2. 강좌 삭제하기");
            System.out.println("3. 강의 등록하기"); // TODO: LectureView로 연결
            System.out.println("----------");
            System.out.println("q. 뒤로 가기");
            
        } catch (Exception e) {
            System.out.println("강좌를 찾을 수 없습니다: " + e.getMessage());
        }
    }
    
    /**
     * 강좌 상세 화면 (강의 목록 포함)
     */
    public void showCourseDetail(long courseId) {
        try {
            CourseDetailResponse course = courseController.findCourseById(courseId);
            
            System.out.println("\n=== " + course.title() + " ===");
            System.out.println(course.description());
            System.out.println("카테고리: " + course.category());
            System.out.println("생성일시: " + course.dateCreated().format(dateFormatter));
            System.out.println("----------");
            
            // TODO: 강의 목록 표시 (LectureController 필요)
            System.out.println("강의 목록:");
            System.out.println("1. 강의1 (예시)");
            System.out.println("2. 강의2 (예시)");
            System.out.println("3. 강의3 (예시)");
            
            System.out.println("----------");
            
            // 수강 신청 메뉴 (수강 중이 아닐 때만 - TODO: 수강 상태 확인 로직 필요)
            System.out.println("a. 수강 신청하기");
            
            // 강좌 관리 메뉴 (instructor만)
            if (course.instructorId() == Session.getUserId()) {
                System.out.println("b. 강좌 관리");
            }
            
            System.out.println("q. 뒤로 가기");
            
        } catch (Exception e) {
            System.out.println("강좌를 찾을 수 없습니다: " + e.getMessage());
        }
    }
    
    /**
     * 강좌 수정 화면
     */
    public void showCourseUpdate(long courseId) {
        try {
            CourseDetailResponse course = courseController.findCourseById(courseId);
            
            // 내 강좌인지 확인
            if (course.instructorId() != Session.getUserId()) {
                System.out.println("자신이 생성한 강좌만 수정할 수 있습니다.");
                return;
            }
            
            System.out.println("\n=== 강좌 수정 ===");
            System.out.println("현재 강좌 정보:");
            displayCourseDetail(course);
            
            System.out.println("\n새 정보를 입력해주세요:");
            String title = inputUtil.getString("강좌 제목: ", "강좌 제목을 입력해주세요.");
            String description = inputUtil.getString("강좌 설명: ", "강좌 설명을 입력해주세요.");
            
            System.out.println("\n카테고리를 선택해주세요:");
            showCategorySelection();
            Category category = selectCategory();
            
            CourseUpdateRequest request = new CourseUpdateRequest(courseId, title, description, category);
            
            if (courseController.updateCourse(request)) {
                System.out.println("강좌가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("강좌 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            System.out.println("강좌 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 강좌 삭제 화면
     */
    public void showCourseDelete(long courseId) {
        try {
            CourseDetailResponse course = courseController.findCourseById(courseId);
            
            // 내 강좌인지 확인
            if (course.instructorId() != Session.getUserId()) {
                System.out.println("자신이 생성한 강좌만 삭제할 수 있습니다.");
                return;
            }
            
            System.out.println("\n=== 강좌 삭제 ===");
            System.out.println("삭제할 강좌 정보:");
            displayCourseDetail(course);
            
            boolean confirmed = inputUtil.getConfirmation("정말로 이 강좌를 삭제하시겠습니까?");
            
            if (confirmed) {
                if (courseController.deleteCourse(courseId)) {
                    System.out.println("강좌가 성공적으로 삭제되었습니다.");
                } else {
                    System.out.println("강좌 삭제에 실패했습니다.");
                }
            } else {
                System.out.println("강좌 삭제를 취소했습니다.");
            }
        } catch (Exception e) {
            System.out.println("강좌 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    /**
     * 카테고리 선택 처리 (헬퍼 메소드)
     */
    public Category selectCategory() {
        int choice = inputUtil.getInt("> ", "1-4 사이의 숫자를 입력해주세요", 1, 4);
        
        return switch (choice) {
            case 1 -> Category.DEVELOPMENT;
            case 2 -> Category.DESIGN;
            case 3 -> Category.MARKETING;
            case 4 -> Category.BUSINESS;
            default -> throw new LXPException("올바른 카테고리를 선택해주세요.");
        };
    }
    
    /**
     * 제목으로 검색 (입력 받아서 검색 결과 화면으로 연결)
     */
    public String getSearchTitleInput() {
        return inputUtil.getString("검색할 제목: ", "제목을 입력해주세요.");
    }
    
    /**
     * 강좌 목록에서 강좌 번호 입력 받기
     */
    public int getCourseSelectionInput(int maxCourseNumber) {
        return inputUtil.getInt("강좌 번호 선택 (1-" + maxCourseNumber + "): ", 
                "유효한 번호를 입력해주세요.", 1, maxCourseNumber);
    }
    
    /**
     * 강좌 목록을 반환 (외부에서 사용하기 위한 유틸리티)
     */
    public List<CourseResponse> getAllCourses() {
        return courseController.findAllCourses();
    }
    
    /**
     * 제목으로 검색한 강좌 목록 반환
     */
    public List<CourseResponse> searchCoursesByTitle(String title) {
        return courseController.searchCoursesByTitle(title);
    }
    
    /**
     * 카테고리로 검색한 강좌 목록 반환
     */
    public List<CourseResponse> searchCoursesByCategory(Category category) {
        return courseController.searchCoursesByCategory(category);
    }

    /**
     * 강좌 목록 표시 (헬퍼 메소드)
     */
    private void displayCourseList(List<CourseResponse> courses) {
        System.out.printf("%-5s %-35s %-15s%n", "번호", "강좌 제목", "카테고리");
        System.out.println("-".repeat(55));
        
        for (int i = 0; i < courses.size(); i++) {
            CourseResponse course = courses.get(i);
            System.out.printf("%-5d %-35s %-15s%n",
                    (i + 1), // 사용자에게는 1부터 시작하는 번호로 표시
                    truncateString(course.title(), 33),
                    course.category());
        }
    }

    /**
     * 강좌 상세 정보 표시 (헬퍼 메소드)
     */
    private void displayCourseDetail(CourseDetailResponse course) {
        System.out.println("제목: " + course.title());
        System.out.println("설명: " + course.description());
        System.out.println("카테고리: " + course.category());
        System.out.println("생성일시: " + course.dateCreated().format(dateFormatter));
    }

    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 2) + "..";
    }

}
