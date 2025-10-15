package com.lxp.lecture.dto;

import com.lxp.exception.LXPException;

public record LectureCreateRequest(
        Long sectionId,
        String title,
        String description
) {
    public LectureCreateRequest {
        validateSectionId(sectionId);
        validateTitle(title);
        validateDescription(description);
    }

    private void validateSectionId(Long sectionId) {
        if (sectionId == null) {
            throw new LXPException("섹션 ID는 null일 수 없습니다.");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new LXPException("강의 제목은 비어 있을 수 없습니다.");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new LXPException("강의 설명은 비어 있을 수 없습니다.");
        }
    }
}
