package com.lxp.lecture.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lxp.exception.LXPException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LectureCreateRequestTest {

    private static Stream<Arguments> invalidLectureArguments() {
        return Stream.of(
                Arguments.of(null, "Valid Description", "강의 제목은 비어 있을 수 없습니다."),
                Arguments.of("  ", "Valid Description", "강의 제목은 비어 있을 수 없습니다."),
                Arguments.of("Valid Title", null, "강의 설명은 비어 있을 수 없습니다."),
                Arguments.of("Valid Title", "  ", "강의 설명은 비어 있을 수 없습니다.")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidLectureArguments")
    @DisplayName("유효하지 않은 인자로 강의 생성을 시도하면 예외가 발생한다.")
    void createWithInvalidArguments(String title, String description, String expectedMessage) {
        // when & then
        assertThatThrownBy(() -> new LectureCreateRequest(title, description))
                .isInstanceOf(LXPException.class)
                .hasMessage(expectedMessage);
    }
}
