package com.lxp.lecture.dto;

import com.lxp.lecture.exception.InvalidCourseIdException;
import com.lxp.lecture.exception.InvalidDescriptionException;
import com.lxp.lecture.exception.InvalidPathException;
import com.lxp.lecture.exception.InvalidTitleException;
import com.lxp.support.StringUtils;

public record LectureCreateRequest(
        Long courseId,
        String title,
        String description,
        String path
) {

    public LectureCreateRequest {
        validateCourseId(courseId);
        validateTitle(title);
        validateDescription(description);
        validatePath(path);
    }

    private void validateCourseId(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new InvalidCourseIdException();
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

    private void validatePath(String path) {
        if (StringUtils.isBlank(path)) {
            throw new InvalidPathException();
        }
    }

}
