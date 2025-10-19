package com.lxp.lecture.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.lxp.global.exception.LXPException;
import com.lxp.lecture.presentation.controller.dto.request.LectureCreateRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LectureCreateRequestTest {

    private static Stream<Arguments> invalidLectureArguments() {
        String validDescription = "Valid Description";
        String validTitle = "Valid Title";
        String validPath = "Valid Path";

        String courseIdExpectedMessage = "코스 ID는 null이거나 0 이하일 수 없습니다.";
        String titleExpectedMessage = "강의 제목은 비어 있을 수 없습니다.";
        String descriptionExpectedMessage = "강의 설명은 비어 있을 수 없습니다.";
        String pathExpectedMessage = "경로는 비어 있을 수 없습니다.";

        return Stream.of(
                Arguments.of(null, validTitle, validDescription, validPath, courseIdExpectedMessage),
                Arguments.of(0L, validTitle, validDescription, validPath, courseIdExpectedMessage),
                Arguments.of(-1L, validTitle, validDescription, validPath, courseIdExpectedMessage),
                Arguments.of(1L, null, validDescription, validPath, titleExpectedMessage),
                Arguments.of(1L, "  ", validDescription, validPath, titleExpectedMessage),
                Arguments.of(1L, validTitle, null, validPath, descriptionExpectedMessage),
                Arguments.of(1L, validTitle, "  ", validPath, descriptionExpectedMessage),
                Arguments.of(1L, validTitle, validDescription, null, pathExpectedMessage),
                Arguments.of(1L, validTitle, validDescription, "  ", pathExpectedMessage)
        );
    }

    @Test
    @DisplayName("유효한 인자로 강의 생성을 시도하면 성공한다.")
    void shouldCreate_whenValidArguments() {
        // given
        Long courseId = 1L;
        String title = "Valid Title";
        String description = "Valid Description";
        String path = "Valid Path";

        // when
        LectureCreateRequest request = new LectureCreateRequest(courseId, title, description, path);

        // then
        assertAll(
                () -> assertThat(request.courseId()).isEqualTo(courseId),
                () -> assertThat(request.title()).isEqualTo(title),
                () -> assertThat(request.description()).isEqualTo(description),
                () -> assertThat(request.path()).isEqualTo(path)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidLectureArguments")
    @DisplayName("유효하지 않은 인자로 강의 생성을 시도하면 예외가 발생한다.")
    void shouldThrowException_whenInvalidArguments(Long courseId, String title, String description, String path,
                                                   String expectedMessage) {
        // when & then
        assertThatThrownBy(() -> new LectureCreateRequest(courseId, title, description, path))
                .isInstanceOf(LXPException.class)
                .hasMessage(expectedMessage);
    }
}
