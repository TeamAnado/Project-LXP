package com.lxp.course;

import com.lxp.course.dao.CourseDao;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.course.exception.InvalidCourseIdException;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.course.service.CourseService;
import com.lxp.course.service.dto.CourseListDto;
import com.lxp.course.service.dto.UpdateCourseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private CourseDao mockCourseDao;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        mockCourseDao = mock(CourseDao.class);
        courseService = new CourseService(mockCourseDao);
    }

    // deleteCourse 테스트
    @Test
    @DisplayName("유효한 ID로 강의 삭제 시 성공한다")
    void shouldReturnTrue_whenDeletingCourseWithValidId() {
        // Arrange
        Long validId = 1L;
        when(mockCourseDao.existsById(validId)).thenReturn(true);
        when(mockCourseDao.delete(validId)).thenReturn(true);

        // Act
        boolean result = courseService.deleteCourse(validId);

        // Assert
        assertThat(result).isTrue();
        verify(mockCourseDao, times(1)).existsById(validId);
        verify(mockCourseDao, times(1)).delete(validId);
    }

    @Test
    @DisplayName("null ID로 강의 삭제 시 InvalidCourseIdException이 발생한다")
    void shouldThrowInvalidCourseIdException_whenDeletingCourseWithNullId() {
        // Act & Assert
        assertThatThrownBy(() -> courseService.deleteCourse(null))
                .isInstanceOf(InvalidCourseIdException.class);

        verify(mockCourseDao, times(0)).existsById(any(Long.class));
        verify(mockCourseDao, times(0)).delete(any(Long.class));
    }

    @Test
    @DisplayName("0 이하 ID로 강의 삭제 시 InvalidCourseIdException이 발생한다")
    void shouldThrowInvalidCourseIdException_whenDeletingCourseWithInvalidId() {
        // Arrange
        Long invalidId = 0L;

        // Act & Assert
        assertThatThrownBy(() -> courseService.deleteCourse(invalidId))
                .isInstanceOf(InvalidCourseIdException.class);

        verify(mockCourseDao, times(0)).existsById(any(Long.class));
        verify(mockCourseDao, times(0)).delete(any(Long.class));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 강의 삭제 시 CourseNotFoundException이 발생한다")
    void shouldThrowCourseNotFoundException_whenDeletingCourseWithNonExistentId() {
        // Arrange
        Long nonExistentId = 999L;
        when(mockCourseDao.existsById(nonExistentId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> courseService.deleteCourse(nonExistentId))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("해당 ID의 강의를 찾을 수 없습니다: " + nonExistentId);

        verify(mockCourseDao, times(1)).existsById(nonExistentId);
        verify(mockCourseDao, times(0)).delete(any(Long.class));
    }

    // updateCourse 테스트
    @Test
    @DisplayName("유효한 데이터로 강의 수정 시 성공한다")
    void shouldReturnTrue_whenUpdatingCourseWithValidData() {
        // Arrange
        Long courseId = 1L;
        LocalDateTime createdTime = LocalDateTime.of(2024, 1, 1, 10, 0);
        Course existingCourse = new Course(courseId, "Original Title", "Original Description",
                100L, Category.DEVELOPMENT, createdTime, createdTime);

        UpdateCourseDto updateDto = new UpdateCourseDto(courseId, "Updated Title",
                "Updated Description", Category.DESIGN);

        when(mockCourseDao.findById(courseId)).thenReturn(existingCourse);
        when(mockCourseDao.update(any(Course.class))).thenReturn(true);

        // Act
        boolean result = courseService.updateCourse(updateDto);

        // Assert
        assertThat(result).isTrue();

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        verify(mockCourseDao, times(1)).update(captor.capture());

        Course updatedCourse = captor.getValue();
        assertAll(
                () -> assertThat(updatedCourse.getId()).isEqualTo(courseId),
                () -> assertThat(updatedCourse.getTitle()).isEqualTo("Updated Title"),
                () -> assertThat(updatedCourse.getDescription()).isEqualTo("Updated Description"),
                () -> assertThat(updatedCourse.getCategory()).isEqualTo(Category.DESIGN),
                () -> assertThat(updatedCourse.getInstructorId()).isEqualTo(100L),
                () -> assertThat(updatedCourse.getDateCreated()).isEqualTo(createdTime),
                () -> assertThat(updatedCourse.getDateModified()).isAfter(createdTime)
        );
    }

    @Test
    @DisplayName("null ID로 강의 수정 시 InvalidCourseIdException이 발생한다")
    void shouldThrowInvalidCourseIdException_whenUpdatingCourseWithNullId() {
        // Arrange
        UpdateCourseDto updateDto = new UpdateCourseDto(null, "Title", "Description", Category.DEVELOPMENT);

        // Act & Assert
        assertThatThrownBy(() -> courseService.updateCourse(updateDto))
                .isInstanceOf(InvalidCourseIdException.class);

        verify(mockCourseDao, times(0)).findById(any(Long.class));
        verify(mockCourseDao, times(0)).update(any(Course.class));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 강의 수정 시 CourseNotFoundException이 발생한다")
    void shouldThrowCourseNotFoundException_whenUpdatingCourseWithNonExistentId() {
        // Arrange
        Long nonExistentId = 999L;
        UpdateCourseDto updateDto = new UpdateCourseDto(nonExistentId, "Title", "Description", Category.DEVELOPMENT);

        when(mockCourseDao.findById(nonExistentId)).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> courseService.updateCourse(updateDto))
                .isInstanceOf(CourseNotFoundException.class)
                .hasMessageContaining("해당 ID의 강의를 찾을 수 없습니다: " + nonExistentId);

        verify(mockCourseDao, times(1)).findById(nonExistentId);
        verify(mockCourseDao, times(0)).update(any(Course.class));
    }

    // findCoursesByTitle 테스트
    @Test
    @DisplayName("제목으로 강의 검색 시 결과를 반환한다")
    void shouldReturnCourses_whenFindingCoursesByMatchingTitle() {
        // Arrange
        String searchTitle = "Java";
        LocalDateTime now = LocalDateTime.now();
        List<Course> mockCourses = Arrays.asList(
                new Course(1L, "Java Programming", "Description 1", 100L, Category.DEVELOPMENT, now, now),
                new Course(2L, "Advanced Java", "Description 2", 101L, Category.DEVELOPMENT, now, now)
        );

        when(mockCourseDao.findByTitleContaining(searchTitle)).thenReturn(mockCourses);

        // Act
        List<CourseListDto> result = courseService.findCoursesByTitle(searchTitle);

        // Assert
        assertThat(result).hasSize(2);
        assertAll(
                () -> assertThat(result.get(0).title()).isEqualTo("Java Programming"),
                () -> assertThat(result.get(1).title()).isEqualTo("Advanced Java")
        );

        verify(mockCourseDao, times(1)).findByTitleContaining(searchTitle);
    }

    @Test
    @DisplayName("제목으로 강의 검색 시 결과가 없으면 빈 리스트를 반환한다")
    void shouldReturnEmptyList_whenFindingCoursesByTitleWithNoMatches() {
        // Arrange
        String searchTitle = "NonExistent";
        when(mockCourseDao.findByTitleContaining(searchTitle)).thenReturn(Collections.emptyList());

        // Act
        List<CourseListDto> result = courseService.findCoursesByTitle(searchTitle);

        // Assert
        assertThat(result).isEmpty();
        verify(mockCourseDao, times(1)).findByTitleContaining(searchTitle);
    }

    // findCoursesByCategory 테스트
    @Test
    @DisplayName("카테고리로 강의 검색 시 결과를 반환한다")
    void shouldReturnCourses_whenFindingCoursesByValidCategory() {
        // Arrange
        Category category = Category.DEVELOPMENT;
        LocalDateTime now = LocalDateTime.now();
        List<Course> mockCourses = Arrays.asList(
                new Course(1L, "Course 1", "Description 1", 100L, Category.DEVELOPMENT, now, now),
                new Course(2L, "Course 2", "Description 2", 101L, Category.DEVELOPMENT, now, now)
        );

        when(mockCourseDao.findByCategory(category)).thenReturn(mockCourses);

        // Act
        List<CourseListDto> result = courseService.findCoursesByCategory(category);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).category()).isEqualTo(Category.DEVELOPMENT);
        assertThat(result.get(1).category()).isEqualTo(Category.DEVELOPMENT);

        verify(mockCourseDao, times(1)).findByCategory(category);
    }

    @Test
    @DisplayName("카테고리로 강의 검색 시 결과가 없으면 빈 리스트를 반환한다")
    void shouldReturnEmptyList_whenFindingCoursesByCategoryWithNoMatches() {
        // Arrange
        Category category = Category.MARKETING;
        when(mockCourseDao.findByCategory(category)).thenReturn(Collections.emptyList());

        // Act
        List<CourseListDto> result = courseService.findCoursesByCategory(category);

        // Assert
        assertThat(result).isEmpty();
        verify(mockCourseDao, times(1)).findByCategory(category);
    }

    // findCoursesByInstructorId 테스트
    @Test
    @DisplayName("강사 ID로 강의 검색 시 결과를 반환한다")
    void shouldReturnCourses_whenFindingCoursesByValidInstructorId() {
        // Arrange
        Long instructorId = 100L;
        LocalDateTime now = LocalDateTime.now();
        List<Course> mockCourses = Arrays.asList(
                new Course(1L, "Course 1", "Description 1", instructorId, Category.DEVELOPMENT, now, now),
                new Course(2L, "Course 2", "Description 2", instructorId, Category.DESIGN, now, now)
        );

        when(mockCourseDao.findByInstructorId(instructorId)).thenReturn(mockCourses);

        // Act
        List<CourseListDto> result = courseService.findCoursesByInstructorId(instructorId);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).instructorId()).isEqualTo(instructorId);
        assertThat(result.get(1).instructorId()).isEqualTo(instructorId);

        verify(mockCourseDao, times(1)).findByInstructorId(instructorId);
    }

    @Test
    @DisplayName("강사 ID로 강의 검색 시 결과가 없으면 빈 리스트를 반환한다")
    void shouldReturnEmptyList_whenFindingCoursesByInstructorIdWithNoMatches() {
        // Arrange
        Long instructorId = 999L;
        when(mockCourseDao.findByInstructorId(instructorId)).thenReturn(Collections.emptyList());

        // Act
        List<CourseListDto> result = courseService.findCoursesByInstructorId(instructorId);

        // Assert
        assertThat(result).isEmpty();
        verify(mockCourseDao, times(1)).findByInstructorId(instructorId);
    }
}
