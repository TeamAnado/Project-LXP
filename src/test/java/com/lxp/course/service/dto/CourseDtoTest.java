package com.lxp.course.service.dto;

import com.lxp.course.model.enums.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Course DTO 테스트")
class CourseDtoTest {

    @Test
    @DisplayName("CreateCourseDto가 올바르게 생성된다")
    void createCourseDto_ShouldBeCreatedCorrectly() {
        // Given
        String title = "Java 프로그래밍";
        String description = "자바 기초부터 고급까지";
        Category category = Category.DEVELOPMENT;
        Long instructorId = 1L;

        // When
        CreateCourseDto dto = new CreateCourseDto(title, description, category, instructorId);

        // Then
        assertAll(
            () -> assertThat(dto.title()).isEqualTo(title),
            () -> assertThat(dto.description()).isEqualTo(description),
            () -> assertThat(dto.category()).isEqualTo(category),
            () -> assertThat(dto.instructorId()).isEqualTo(instructorId)
        );
    }

    @Test
    @DisplayName("CourseListDto가 올바르게 생성된다")
    void courseListDto_ShouldBeCreatedCorrectly() {
        // Given
        Long id = 1L;
        String title = "Spring Boot 개발";
        Category category = Category.DEVELOPMENT;
        Long instructorId = 2L;

        // When
        CourseListDto dto = new CourseListDto(id, title, category, instructorId);

        // Then
        assertAll(
            () -> assertThat(dto.id()).isEqualTo(id),
            () -> assertThat(dto.title()).isEqualTo(title),
            () -> assertThat(dto.category()).isEqualTo(category),
            () -> assertThat(dto.instructorId()).isEqualTo(instructorId)
        );
    }

    @Test
    @DisplayName("CourseDetailDto가 올바르게 생성된다")
    void courseDetailDto_ShouldBeCreatedCorrectly() {
        // Given
        Long id = 1L;
        String title = "React 개발";
        String description = "React로 웹 앱 만들기";
        Category category = Category.DEVELOPMENT;
        Long instructorId = 3L;
        LocalDateTime dateCreated = LocalDateTime.now();

        // When
        CourseDetailDto dto = new CourseDetailDto(id, title, description, category, instructorId, dateCreated);

        // Then
        assertAll(
            () -> assertThat(dto.id()).isEqualTo(id),
            () -> assertThat(dto.title()).isEqualTo(title),
            () -> assertThat(dto.description()).isEqualTo(description),
            () -> assertThat(dto.category()).isEqualTo(category),
            () -> assertThat(dto.instructorId()).isEqualTo(instructorId),
            () -> assertThat(dto.dateCreated()).isEqualTo(dateCreated)
        );
    }

    @Test
    @DisplayName("DTO 간 데이터 변환이 올바르게 동작한다")
    void dtoConversion_ShouldWorkCorrectly() {
        // Given
        CreateCourseDto createDto = new CreateCourseDto(
            "Node.js 개발", 
            "Node.js 백엔드 개발", 
            Category.DEVELOPMENT, 
            4L
        );

        // When - CreateDto의 데이터로 다른 DTO들 생성
        CourseListDto listDto = new CourseListDto(
            1L, 
            createDto.title(), 
            createDto.category(), 
            createDto.instructorId()
        );

        CourseDetailDto detailDto = new CourseDetailDto(
            1L, 
            createDto.title(), 
            createDto.description(), 
            createDto.category(), 
            createDto.instructorId(), 
            LocalDateTime.now()
        );

        // Then
        assertAll(
            () -> assertThat(listDto.title()).isEqualTo(createDto.title()),
            () -> assertThat(listDto.category()).isEqualTo(createDto.category()),
            () -> assertThat(listDto.instructorId()).isEqualTo(createDto.instructorId()),
            () -> assertThat(detailDto.title()).isEqualTo(createDto.title()),
            () -> assertThat(detailDto.description()).isEqualTo(createDto.description()),
            () -> assertThat(detailDto.category()).isEqualTo(createDto.category()),
            () -> assertThat(detailDto.instructorId()).isEqualTo(createDto.instructorId())
        );
    }

    @Test
    @DisplayName("DTO의 equals와 hashCode가 올바르게 동작한다")
    void dtoEqualsAndHashCode_ShouldWorkCorrectly() {
        // Given
        CreateCourseDto dto1 = new CreateCourseDto("Title", "Description", Category.DESIGN, 1L);
        CreateCourseDto dto2 = new CreateCourseDto("Title", "Description", Category.DESIGN, 1L);
        CreateCourseDto dto3 = new CreateCourseDto("Different Title", "Description", Category.DESIGN, 1L);

        // Then
        assertAll(
            () -> assertThat(dto1).isEqualTo(dto2),
            () -> assertThat(dto1).isNotEqualTo(dto3),
            () -> assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode()),
            () -> assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode())
        );
    }

    @Test
    @DisplayName("DTO의 toString이 올바르게 동작한다")
    void dtoToString_ShouldWorkCorrectly() {
        // Given
        CreateCourseDto dto = new CreateCourseDto("Test Course", "Test Description", Category.MARKETING, 5L);

        // When
        String toString = dto.toString();

        // Then
        assertAll(
            () -> assertThat(toString).contains("Test Course"),
            () -> assertThat(toString).contains("Test Description"),
            () -> assertThat(toString).contains("MARKETING"),
            () -> assertThat(toString).contains("5")
        );
    }

    @Test
    @DisplayName("DTO가 null 값을 올바르게 처리한다")
    void dtoWithNullValues_ShouldHandleCorrectly() {
        // When
        CreateCourseDto dtoWithNulls = new CreateCourseDto(null, null, null, null);

        // Then
        assertAll(
            () -> assertThat(dtoWithNulls.title()).isNull(),
            () -> assertThat(dtoWithNulls.description()).isNull(),
            () -> assertThat(dtoWithNulls.category()).isNull(),
            () -> assertThat(dtoWithNulls.instructorId()).isNull()
        );
    }
}