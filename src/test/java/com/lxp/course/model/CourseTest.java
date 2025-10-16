package com.lxp.course.model;

import com.lxp.course.model.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Course 모델 테스트")
class CourseTest {

    @Test
    @DisplayName("새로운 Course 생성 시 필드가 올바르게 설정된다")
    void createNewCourse_ShouldSetFieldsCorrectly() {
        // Given
        String title = "Java 프로그래밍 기초";
        String description = "자바 프로그래밍을 배우는 강의입니다";
        Long instructorId = 1L;
        Category category = Category.DEVELOPMENT;

        // When
        Course course = new Course(title, description, instructorId, category);

        // Then
        assertAll(
            () -> assertThat(course.getTitle()).isEqualTo(title),
            () -> assertThat(course.getDescription()).isEqualTo(description),
            () -> assertThat(course.getInstructorId()).isEqualTo(instructorId),
            () -> assertThat(course.getCategory()).isEqualTo(category),
            () -> assertThat(course.getDateCreated()).isNotNull(),
            () -> assertThat(course.getDateModified()).isNotNull(),
            () -> assertThat(course.getId()).isNull() // 새로 생성된 객체는 ID가 없음
        );
    }

    @Test
    @DisplayName("데이터베이스에서 읽은 Course 생성 시 모든 필드가 올바르게 설정된다")
    void createCourseFromDatabase_ShouldSetAllFieldsCorrectly() {
        // Given
        Long id = 1L;
        String title = "Spring Boot 개발";
        String description = "Spring Boot로 웹 개발하기";
        Long instructorId = 2L;
        Category category = Category.DEVELOPMENT;
        LocalDateTime dateCreated = LocalDateTime.now().minusDays(1);
        LocalDateTime dateModified = LocalDateTime.now();

        // When
        Course course = new Course(id, title, description, instructorId, category, dateCreated, dateModified);

        // Then
        assertAll(
            () -> assertThat(course.getId()).isEqualTo(id),
            () -> assertThat(course.getTitle()).isEqualTo(title),
            () -> assertThat(course.getDescription()).isEqualTo(description),
            () -> assertThat(course.getInstructorId()).isEqualTo(instructorId),
            () -> assertThat(course.getCategory()).isEqualTo(category),
            () -> assertThat(course.getDateCreated()).isEqualTo(dateCreated),
            () -> assertThat(course.getDateModified()).isEqualTo(dateModified)
        );
    }

    @Test
    @DisplayName("Course 생성 시 날짜가 자동으로 설정된다")
    void createCourse_ShouldSetDateAutomatically() {
        // Given
        LocalDateTime before = LocalDateTime.now();

        // When
        Course course = new Course("Test Title", "Test Description", 1L, Category.DEVELOPMENT);

        // Then
        LocalDateTime after = LocalDateTime.now();
        assertAll(
            () -> assertThat(course.getDateCreated()).isAfter(before.minusSeconds(1)),
            () -> assertThat(course.getDateCreated()).isBefore(after.plusSeconds(1)),
            () -> assertThat(course.getDateModified()).isAfter(before.minusSeconds(1)),
            () -> assertThat(course.getDateModified()).isBefore(after.plusSeconds(1))
        );
    }

    @Test
    @DisplayName("모든 Category enum 값이 올바르게 설정된다")
    void createCourse_WithAllCategories_ShouldWork() {
        // Given & When & Then
        for (Category category : Category.values()) {
            Course course = new Course("Title", "Description", 1L, category);
            assertThat(course.getCategory()).isEqualTo(category);
        }
    }
}