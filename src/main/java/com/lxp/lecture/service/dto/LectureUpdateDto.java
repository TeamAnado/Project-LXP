package com.lxp.lecture.service.dto;

import com.lxp.lecture.model.Lecture;
import java.time.LocalDateTime;

public record LectureUpdateDto(
        long id,
        Long courseId,
        String title,
        String description,
        String path
) {

    public Lecture to(Lecture existingLecture) {
        Long newCourseId = this.courseId != null ? this.courseId : existingLecture.getCourseId();
        String newTitle = this.title != null ? this.title : existingLecture.getTitle();
        String newDescription = this.description != null ? this.description : existingLecture.getDescription();
        String newPath = this.path != null ? this.path : existingLecture.getPath();

        return new Lecture(
                existingLecture.getId(),
                newCourseId,
                newTitle,
                newDescription,
                newPath,
                existingLecture.getDateCreated(),
                LocalDateTime.now()
        );
    }
}
