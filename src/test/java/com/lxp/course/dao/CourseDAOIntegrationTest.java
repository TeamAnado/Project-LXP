package com.lxp.course.dao;

import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Integration tests for CourseDAO
 * Note: These tests require a test database to be configured
 * They are marked with @Disabled by default to avoid failing the build
 * Remove @Disabled and configure test database to run these tests
 */
@DisplayName("CourseDAO 통합 테스트")
@Disabled("Database configuration required for integration tests")
class CourseDAOIntegrationTest {

    private CourseDAO courseDAO;

    @BeforeEach
    void setUp() throws SQLException {
        courseDAO = new CourseDAO();
        // Setup test data if needed
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Clean up test data if needed
        // DELETE FROM courses WHERE title LIKE 'TEST_%'
    }

    @Test
    @DisplayName("강의 저장 후 생성된 ID를 반환한다")
    void save_ShouldReturnGeneratedId() throws SQLException {
        // Given
        Course course = new Course(
            "TEST_Java Programming", 
            "TEST_Learn Java basics", 
            1L, 
            Category.DEVELOPMENT
        );

        // When
        long savedId = courseDAO.save(course);

        // Then
        assertThat(savedId).isPositive();
    }

    @Test
    @DisplayName("저장된 강의를 ID로 조회할 수 있다")
    void findById_ShouldReturnSavedCourse() throws SQLException {
        // Given
        Course originalCourse = new Course(
            "TEST_Spring Boot", 
            "TEST_Learn Spring Boot", 
            2L, 
            Category.DEVELOPMENT
        );
        long savedId = courseDAO.save(originalCourse);

        // When
        Course foundCourse = courseDAO.findById(savedId);

        // Then
        assertAll(
            () -> assertThat(foundCourse).isNotNull(),
            () -> assertThat(foundCourse.getId()).isEqualTo(savedId),
            () -> assertThat(foundCourse.getTitle()).isEqualTo("TEST_Spring Boot"),
            () -> assertThat(foundCourse.getDescription()).isEqualTo("TEST_Learn Spring Boot"),
            () -> assertThat(foundCourse.getInstructorId()).isEqualTo(2L),
            () -> assertThat(foundCourse.getCategory()).isEqualTo(Category.DEVELOPMENT),
            () -> assertThat(foundCourse.getDateCreated()).isNotNull(),
            () -> assertThat(foundCourse.getDateModified()).isNotNull()
        );
    }

    @Test
    @DisplayName("존재하지 않는 ID로 조회 시 null을 반환한다")
    void findById_WhenNotExists_ShouldReturnNull() throws SQLException {
        // When
        Course course = courseDAO.findById(999999L);

        // Then
        assertThat(course).isNull();
    }

    @Test
    @DisplayName("모든 강의를 조회할 수 있다")
    void findAll_ShouldReturnAllCourses() throws SQLException {
        // Given - Save some test courses first
        Course course1 = new Course("TEST_Course1", "Description1", 1L, Category.DEVELOPMENT);
        Course course2 = new Course("TEST_Course2", "Description2", 2L, Category.DESIGN);
        courseDAO.save(course1);
        courseDAO.save(course2);

        // When
        List<Course> courses = courseDAO.findAll();

        // Then
        assertThat(courses).isNotEmpty();
        assertThat(courses).extracting(Course::getTitle)
            .contains("TEST_Course1", "TEST_Course2");
    }

    @Test
    @DisplayName("제목에 키워드가 포함된 강의를 검색할 수 있다")
    void findByTitleContaining_ShouldReturnMatchingCourses() throws SQLException {
        // Given
        Course javaBasic = new Course("TEST_Java Basic", "Java basics", 1L, Category.DEVELOPMENT);
        Course javaAdvanced = new Course("TEST_Java Advanced", "Advanced Java", 1L, Category.DEVELOPMENT);
        Course pythonBasic = new Course("TEST_Python Basic", "Python basics", 2L, Category.DEVELOPMENT);
        
        courseDAO.save(javaBasic);
        courseDAO.save(javaAdvanced);
        courseDAO.save(pythonBasic);

        // When
        List<Course> javaCourses = courseDAO.findByTitleContaining("Java");

        // Then
        assertAll(
            () -> assertThat(javaCourses).hasSize(2),
            () -> assertThat(javaCourses).extracting(Course::getTitle)
                .containsExactlyInAnyOrder("TEST_Java Basic", "TEST_Java Advanced"),
            () -> assertThat(javaCourses).extracting(Course::getTitle)
                .doesNotContain("TEST_Python Basic")
        );
    }

    @Test
    @DisplayName("키워드와 일치하는 강의가 없으면 빈 리스트를 반환한다")
    void findByTitleContaining_WhenNoMatches_ShouldReturnEmptyList() throws SQLException {
        // When
        List<Course> courses = courseDAO.findByTitleContaining("NonExistentKeyword");

        // Then
        assertThat(courses).isEmpty();
    }

    @Test
    @DisplayName("null 값으로 강의 저장 시 SQLException이 발생한다")
    void save_WithNullValues_ShouldThrowSQLException() {
        // Given
        Course courseWithNullTitle = new Course(null, "Description", 1L, Category.DEVELOPMENT);

        // When & Then
        assertThatThrownBy(() -> courseDAO.save(courseWithNullTitle))
            .isInstanceOf(SQLException.class);
    }

    @Test
    @DisplayName("잘못된 카테고리로 조회 시 예외가 발생한다")
    void findAll_WithInvalidCategoryInDB_ShouldHandleGracefully() {
        // This test would require manually inserting invalid category data
        // and testing how the DAO handles it
        // Implementation depends on your error handling strategy
    }
}