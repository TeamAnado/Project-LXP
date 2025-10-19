package com.lxp.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lxp.course.dao.CourseDAO;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.exception.LectureNotFoundException;
import com.lxp.lecture.exception.LectureNotUpdatedException;
import com.lxp.lecture.model.Lecture;
import com.lxp.lecture.service.LectureService;
import com.lxp.lecture.service.dto.LectureUpdateDto;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class LectureUpdateTest {

    private final LectureDao mockLectureDao = mock(LectureDao.class);
    private final CourseDAO mockCourseDao = mock(CourseDAO.class);
    private final LectureService lectureService = new LectureService(mockLectureDao, mockCourseDao);

    @Test
    @DisplayName("강의 정보가 정상적으로 수정된다.")
    void shouldUpdateLecture() throws SQLException {
        // given
        long lectureId = 1L;
        long courseId = 1L;
        Lecture existingLecture = new Lecture(lectureId, courseId, "Old Title", "Old Desc", "/old/path",
                LocalDateTime.now(), LocalDateTime.now());
        LectureUpdateDto updateDto = new LectureUpdateDto(lectureId, 2L, "New Title", null, null);

        when(mockLectureDao.findById(lectureId)).thenReturn(Optional.of(existingLecture));
        when(mockCourseDao.existsById(2L)).thenReturn(true);

        // when
        lectureService.update(updateDto);

        // then
        ArgumentCaptor<Lecture> captor = ArgumentCaptor.forClass(Lecture.class);
        verify(mockLectureDao, times(1)).update(captor.capture());
        Lecture updatedLecture = captor.getValue();

        assertAll(
                () -> assertThat(updatedLecture.getId()).isEqualTo(lectureId),
                () -> assertThat(updatedLecture.getCourseId()).isEqualTo(2L),
                () -> assertThat(updatedLecture.getTitle()).isEqualTo("New Title"),
                () -> assertThat(updatedLecture.getDescription()).isEqualTo("Old Desc"),
                () -> assertThat(updatedLecture.getPath()).isEqualTo("/old/path")
        );
    }

    @Test
    @DisplayName("수정하려는 강의가 존재하지 않으면 예외가 발생한다.")
    void shouldThrowException_whenLectureNotFound() {
        // given
        long nonExistentLectureId = 999L;
        LectureUpdateDto dto = new LectureUpdateDto(nonExistentLectureId, null, "New Title", null, null);

        when(mockLectureDao.findById(nonExistentLectureId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> lectureService.update(dto))
                .isInstanceOf(LectureNotFoundException.class);
    }

    @Test
    @DisplayName("새로운 코스 ID가 존재하지 않으면 예외가 발생한다.")
    void shouldThrowException_whenCourseNotFound() throws SQLException {
        // given
        long lectureId = 1L;
        long courseId = 1L;
        long nonExistentCourseId = 999L;
        Lecture existingLecture = new Lecture(lectureId, courseId, "Old Title", "Old Desc", "/old/path",
                LocalDateTime.now(), LocalDateTime.now());
        LectureUpdateDto dto = new LectureUpdateDto(lectureId, nonExistentCourseId, null, null, null);

        when(mockLectureDao.findById(lectureId)).thenReturn(Optional.of(existingLecture));
        when(mockCourseDao.existsById(nonExistentCourseId)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> lectureService.update(dto))
                .isInstanceOf(CourseNotFoundException.class);
    }

    @Test
    @DisplayName("수정 중 데이터베이스 오류 발생 시, 서비스 예외가 발생한다.")
    void shouldThrowServiceException_whenDatabaseFailsOnUpdate() {
        // given
        long lectureId = 1L;
        Lecture existingLecture = new Lecture(lectureId, 1L, "Old Title", "Old Desc", "/old/path", LocalDateTime.now(),
                LocalDateTime.now());
        LectureUpdateDto dto = new LectureUpdateDto(lectureId, null, "New Title", null, null);

        when(mockLectureDao.findById(lectureId)).thenReturn(Optional.of(existingLecture));
        doThrow(new LectureNotUpdatedException()).when(mockLectureDao)
                .update(any(Lecture.class));

        // when & then
        assertThatThrownBy(() -> lectureService.update(dto))
                .isInstanceOf(LectureNotUpdatedException.class)
                .hasMessageContaining("강의 수정 중 데이터베이스 오류");
    }
}
