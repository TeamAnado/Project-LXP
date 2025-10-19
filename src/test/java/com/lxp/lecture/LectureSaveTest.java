package com.lxp.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lxp.course.dao.CourseDAO;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.exception.LectureNotSavedException;
import com.lxp.lecture.model.Lecture;
import com.lxp.lecture.presentation.controller.dto.response.LectureCreateResponse;
import com.lxp.lecture.service.LectureService;
import com.lxp.lecture.service.dto.LectureSaveDto;
import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class LectureSaveTest {
    private final LectureDao mockLectureDao = mock(LectureDao.class);
    private final CourseDAO mockCourseDao = mock(CourseDAO.class);
    private final LectureService lectureService = new LectureService(mockLectureDao, mockCourseDao);

    @Test
    @DisplayName("정상적으로 강의가 저장된다.")
    void shouldSaveLecture() throws SQLException {
        // given
        long courseId = 1L;
        long lectureId = 1L;
        LectureSaveDto dto = new LectureSaveDto(courseId, "테스트 강의", "test", "testPath");

        when(mockCourseDao.existsById(courseId)).thenReturn(true);
        when(mockLectureDao.save(any(Lecture.class))).thenReturn(lectureId);

        // when
        LectureCreateResponse response = lectureService.save(dto);

        // then
        verify(mockCourseDao, times(1)).existsById(courseId);
        ArgumentCaptor<Lecture> captor = ArgumentCaptor.forClass(Lecture.class);

        verify(mockLectureDao, times(1)).save(captor.capture());
        Lecture saved = captor.getValue();

        assertAll(
                () -> assertThat(saved.getCourseId()).isEqualTo(courseId),
                () -> assertThat(saved.getTitle()).isEqualTo(dto.title()),
                () -> assertThat(saved.getDescription()).isEqualTo(dto.description()),
                () -> assertThat(saved.getPath()).isEqualTo(dto.path()),
                () -> assertThat(response.id()).isEqualTo(lectureId)
        );
    }

    @Test
    @DisplayName("DAO에서 강의 저장 실패 시, 예외가 발생한다")
    void shouldThrowException_whenDaoSaveFails() throws SQLException {
        // given
        long courseId = 1L;
        LectureSaveDto dto = new LectureSaveDto(courseId, "Valid Title", "description", "testPath");

        when(mockCourseDao.existsById(courseId)).thenReturn(true);
        when(mockLectureDao.save(any(Lecture.class)))
                .thenThrow(new LectureNotSavedException());

        // when & then
        assertThatThrownBy(() -> lectureService.save(dto))
                .isInstanceOf(LectureNotSavedException.class)
                .hasMessage("강의 저장 중 데이터베이스 오류가 발생했습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 코스 ID로 강의 저장을 시도하면, 예외가 발생한다")
    void shouldThrowCourseNotFoundException_whenCourseIdNotExists() throws SQLException {
        // given
        long invalidCourseId = 999L;
        LectureSaveDto dto = new LectureSaveDto(invalidCourseId, "Valid Title", "description", "testPath");

        when(mockCourseDao.existsById(invalidCourseId)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> lectureService.save(dto))
                .isInstanceOf(CourseNotFoundException.class);

        verify(mockLectureDao, times(0)).save(any(Lecture.class));
    }
}
