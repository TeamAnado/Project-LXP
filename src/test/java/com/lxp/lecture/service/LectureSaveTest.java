package com.lxp.lecture.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lxp.course.dao.CourseDao;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.dto.LectureCreateRequest;
import com.lxp.lecture.dto.LectureCreateResponse;
import com.lxp.lecture.exception.LectureNotSavedException;
import com.lxp.lecture.model.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureSaveTest {
    private final LectureDao mockLectureDao = mock(LectureDao.class);
    private final CourseDao mockCourseDao = mock(CourseDao.class);
    private final LectureService lectureService = new LectureService(mockLectureDao, mockCourseDao);

    @Test
    @DisplayName("정상적으로 강의가 저장된다.")
    void shouldSaveLecture() {
        // given
        long courseId = 1L;
        long lectureId = 1L;
        String title = "테스트 강의";
        String description = "test";
        String path = "testPath";

        LectureCreateRequest request = new LectureCreateRequest(courseId, title, description, path);

        when(mockCourseDao.existsById(courseId)).thenReturn(true);
        when(mockLectureDao.save(any(Lecture.class))).thenReturn(lectureId);

        // when
        LectureCreateResponse response = lectureService.save(request);

        // then
        verify(mockCourseDao, times(1)).existsById(courseId);
        verify(mockLectureDao, times(1)).save(any(Lecture.class));
        assertThat(response.id()).isEqualTo(lectureId);
    }

    @Test
    @DisplayName("DAO에서 강의 저장 실패 시, 예외가 발생한다")
    void shouldThrowException_whenDaoSaveFails() {
        // given
        long courseId = 1L;
        String title = "Valid Title";
        String description = "description";
        String path = "testPath";

        LectureCreateRequest request = new LectureCreateRequest(courseId, title, description, path);

        when(mockCourseDao.existsById(courseId)).thenReturn(true);
        when(mockLectureDao.save(any(Lecture.class)))
                .thenThrow(new LectureNotSavedException());

        // when & then
        assertThatThrownBy(() -> lectureService.save(request))
                .isInstanceOf(LectureNotSavedException.class)
                .hasMessageContaining("강의 저장에 실패했습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 코스 ID로 강의 저장을 시도하면, 예외가 발생한다")
    void shouldThrowCourseNotFoundException_whenCourseIdNotExists() {
        // given
        long invalidCourseId = 999L;
        String title = "Valid Title";
        String description = "description";
        String path = "testPath";

        LectureCreateRequest request = new LectureCreateRequest(invalidCourseId, title, description, path);

        when(mockCourseDao.existsById(invalidCourseId)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> lectureService.save(request))
                .isInstanceOf(CourseNotFoundException.class);

        verify(mockLectureDao, times(0)).save(any(Lecture.class));
    }
}
