package com.lxp.course.service;

import com.lxp.course.dao.CourseDAO;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.CourseDetailDto;
import com.lxp.course.service.dto.CourseListDto;
import com.lxp.course.service.dto.CreateCourseDto;
import com.lxp.global.exception.LXPException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CourseService 테스트")
class CourseServiceTest {

    @Mock
    private CourseDAO courseDAO;

    private CourseService courseService;

    @BeforeEach
    void setUp() {
        // CourseService에 Mock DAO를 주입하기 위해 생성자를 수정해야 함
        // 현재는 생성자에서 직접 CourseDAO를 생성하므로, 의존성 주입이 가능하도록 수정 필요
        courseService = new CourseService(courseDAO);
    }

    @Test
    @DisplayName("새로운 강의 생성 시 성공적으로 저장된다")
    void createCourse_ShouldSaveSuccessfully() throws SQLException {
        // Given
        CreateCourseDto dto = new CreateCourseDto(
            "Java 기초",
            "자바 프로그래밍 기초 과정",
            Category.DEVELOPMENT,
            1L
        );
        Long expectedId = 1L;
        when(courseDAO.save(any(Course.class))).thenReturn(expectedId);

        // When
        Long actualId = courseService.createCourse(dto);

        // Then
        assertThat(actualId).isEqualTo(expectedId);
        verify(courseDAO, times(1)).save(any(Course.class));
    }

    @Test
    @DisplayName("모든 강의 조회 시 CourseListDto 리스트를 반환한다")
    void findAllCourses_ShouldReturnCourseListDtoList() throws SQLException {
        // Given
        Course course1 = createSampleCourse(1L, "Java 기초", Category.DEVELOPMENT);
        Course course2 = createSampleCourse(2L, "UI/UX 디자인", Category.DESIGN);
        List<Course> mockCourses = Arrays.asList(course1, course2);

        when(courseDAO.findAll()).thenReturn(mockCourses);

        // When
        List<CourseListDto> result = courseService.findAllCourses();

        // Then
        assertAll(
            () -> assertThat(result).hasSize(2),
            () -> assertThat(result.get(0).title()).isEqualTo("Java 기초"),
            () -> assertThat(result.get(0).category()).isEqualTo(Category.DEVELOPMENT),
            () -> assertThat(result.get(1).title()).isEqualTo("UI/UX 디자인"),
            () -> assertThat(result.get(1).category()).isEqualTo(Category.DESIGN)
        );
        verify(courseDAO, times(1)).findAll();
    }

    @Test
    @DisplayName("ID로 강의 조회 시 존재하는 강의면 CourseDetailDto를 반환한다")
    void findCourseById_WhenCourseExists_ShouldReturnCourseDetailDto() throws SQLException {
        // Given
        Long courseId = 1L;
        Course mockCourse = createSampleCourse(courseId, "Java 고급", Category.DEVELOPMENT);
        when(courseDAO.findById(courseId)).thenReturn(mockCourse);

        // When
        CourseDetailDto result = courseService.findCourseById(courseId);

        // Then
        assertAll(
            () -> assertThat(result.id()).isEqualTo(courseId),
            () -> assertThat(result.title()).isEqualTo("Java 고급"),
            () -> assertThat(result.description()).isEqualTo("Sample Description"),
            () -> assertThat(result.category()).isEqualTo(Category.DEVELOPMENT),
            () -> assertThat(result.instructorId()).isEqualTo(1L),
            () -> assertThat(result.dateCreated()).isNotNull()
        );
        verify(courseDAO, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("ID로 강의 조회 시 존재하지 않으면 LXPException이 발생한다")
    void findCourseById_WhenCourseNotExists_ShouldThrowLXPException() throws SQLException {
        // Given
        Long courseId = 999L;
        when(courseDAO.findById(courseId)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> courseService.findCourseById(courseId))
            .isInstanceOf(LXPException.class)
            .hasMessageContaining("해당 ID의 강의를 찾을 수 없습니다: " + courseId);
        
        verify(courseDAO, times(1)).findById(courseId);
    }

    @Test
    @DisplayName("제목으로 강의 검색 시 일치하는 강의 리스트를 반환한다")
    void findCourseByTitle_ShouldReturnMatchingCourses() throws SQLException {
        // Given
        String keyword = "Java";
        Course course1 = createSampleCourse(1L, "Java 기초", Category.DEVELOPMENT);
        Course course2 = createSampleCourse(2L, "Java 고급", Category.DEVELOPMENT);
        List<Course> mockCourses = Arrays.asList(course1, course2);

        when(courseDAO.findByTitleContaining(keyword)).thenReturn(mockCourses);

        // When
        List<CourseListDto> result = courseService.findCourseByTitle(keyword);

        // Then
        assertAll(
            () -> assertThat(result).hasSize(2),
            () -> assertThat(result.get(0).title()).contains("Java"),
            () -> assertThat(result.get(1).title()).contains("Java")
        );
        verify(courseDAO, times(1)).findByTitleContaining(keyword);
    }

    @Test
    @DisplayName("제목 검색 시 일치하는 강의가 없으면 빈 리스트를 반환한다")
    void findCourseByTitle_WhenNoMatches_ShouldReturnEmptyList() throws SQLException {
        // Given
        String keyword = "NonExistent";
        when(courseDAO.findByTitleContaining(keyword)).thenReturn(List.of());

        // When
        List<CourseListDto> result = courseService.findCourseByTitle(keyword);

        // Then
        assertThat(result).isEmpty();
        verify(courseDAO, times(1)).findByTitleContaining(keyword);
    }

    @Test
    @DisplayName("DAO에서 SQLException 발생 시 예외가 전파된다")
    void whenDAOThrowsSQLException_ShouldPropagate() throws SQLException {
        // Given
        when(courseDAO.findAll()).thenThrow(new SQLException("Database connection failed"));

        // When & Then
        assertThatThrownBy(() -> courseService.findAllCourses())
            .isInstanceOf(SQLException.class)
            .hasMessageContaining("Database connection failed");
    }

    // Helper method to create sample Course objects for testing
    private Course createSampleCourse(Long id, String title, Category category) {
        LocalDateTime now = LocalDateTime.now();
        return new Course(id, title, "Sample Description", 1L, category, now, now);
    }
}