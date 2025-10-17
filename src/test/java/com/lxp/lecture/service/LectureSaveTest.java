package com.lxp.lecture.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.dto.LectureCreateRequest;
import com.lxp.lecture.dto.LectureCreateResponse;
import com.lxp.lecture.exception.LectureNotSavedException;
import com.lxp.lecture.model.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureSaveTest {
    private final LectureDao mockLectureDao = mock(LectureDao.class);
    private final LectureService lectureService = new LectureService(mockLectureDao);

    @Test
    @DisplayName("정상적으로 강의가 저장된다.")
    void shouldSaveLecture() {
        // given
        Long id = 1L;
        String title = "테스트 강의";
        String description = "test";

        LectureCreateRequest request = new LectureCreateRequest(title, description);

        when(mockLectureDao.save(any(Lecture.class)))
                .thenReturn(id);

        // when
        LectureCreateResponse response = lectureService.save(request);

        // then
        verify(mockLectureDao, times(1)).save(any(Lecture.class));
        assertThat(response.id()).isEqualTo(id);
    }

    @Test
    @DisplayName("DAO에서 강의 저장 실패 시, 예외가 발생한다")
    void shouldThrowException_whenDaoSaveFails() {
        // given
        String title = "Valid Title";
        String description = "description";

        LectureCreateRequest request = new LectureCreateRequest(title, description);

        when(mockLectureDao.save(any(Lecture.class)))
                .thenThrow(new LectureNotSavedException());

        // when & then
        assertThatThrownBy(() -> lectureService.save(request))
                .isInstanceOf(LectureNotSavedException.class)
                .hasMessageContaining("강의 저장에 실패했습니다.");
    }
}
