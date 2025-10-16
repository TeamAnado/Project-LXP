package com.lxp.lecture.dto;

import com.lxp.lecture.exception.InvalidDescriptionException;
import com.lxp.lecture.exception.InvalidSectionIdException;
import com.lxp.lecture.exception.InvalidTitleException;
import com.lxp.support.StringUtils;

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
            throw new InvalidSectionIdException();
        }
    }

    private void validateTitle(String title) {
        if (StringUtils.isBlank(title)) {
            throw new InvalidTitleException();
        }
    }

    private void validateDescription(String description) {
        if (StringUtils.isBlank(description)) {
            throw new InvalidDescriptionException();
        }
    }
}
