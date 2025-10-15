package com.lxp.lecture.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lxp.exception.LXPException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.dto.LectureCreateRequest;
import com.lxp.lecture.dto.LectureCreateResponse;
import com.lxp.lecture.model.Lecture;
import com.lxp.section.dao.SectionDao;
import com.lxp.section.domain.Section;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureSaveTest {
    private final LectureDao mockLectureDao = mock(LectureDao.class);
    private final SectionDao mockSectionDao = mock(SectionDao.class);
    private final LectureService lectureService = new LectureService(mockLectureDao, mockSectionDao);

    @Test
    @DisplayName("정상적으로 강의가 저장된다.")
    void shouldSaveLecture() {
        // given
        Long id = 1L;
        Long sectionId = 1L;
        String title = "테스트 강의";
        String description = "test";

        LectureCreateRequest request = new LectureCreateRequest(sectionId, title, description);

        when(mockSectionDao.findById(sectionId))
                .thenReturn(Optional.of(new Section(sectionId, 1L, "어떤 강좌")));

        when(mockLectureDao.save(any(Lecture.class)))
                .thenReturn(id);

        // when
        LectureCreateResponse response = lectureService.save(request);

        // then
        verify(mockLectureDao, times(1)).save(any(Lecture.class));
        assertThat(response.id()).isEqualTo(id);
    }

    @Test
    @DisplayName("강의 등록 시 존재하지 않는 섹션 ID로 저장하면 예외가 발생한다.")
    void shouldThrowException_whenSaveLecture() {
        // given
        Long sectionId = 999L;
        String title = "exception title";
        String description = "exception description";

        LectureCreateRequest request = new LectureCreateRequest(sectionId, title, description);
        when(mockSectionDao.findById(sectionId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> lectureService.save(request))
                .isInstanceOf(LXPException.class)
                .hasMessageContaining("존재하지 않는 섹션 ID 입니다.");

        verify(mockLectureDao, never()).save(any(Lecture.class));
    }

    @Test
    @DisplayName("DAO에서 강의 저장 실패 시, 예외가 발생한다")
    void shouldThrowException_whenDaoSaveFails() {
        // given
        Long sectionId = 1L;
        String title = "Valid Title";
        String description = "description";

        LectureCreateRequest request = new LectureCreateRequest(sectionId, title, description);

        when(mockSectionDao.findById(sectionId))
                .thenReturn(Optional.of(new Section(1L, 1L, "Some Section")));

        when(mockLectureDao.save(any(Lecture.class)))
                .thenThrow(new LXPException("데이터베이스 저장 실패"));

        // when & then
        assertThatThrownBy(() -> lectureService.save(request))
                .isInstanceOf(LXPException.class)
                .hasMessageContaining("데이터베이스 저장 실패");
    }
}
