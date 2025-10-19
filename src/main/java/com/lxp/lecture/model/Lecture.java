package com.lxp.lecture.model;

import com.lxp.global.base.BaseDateModifiedEntity;
import java.time.LocalDateTime;

public class Lecture extends BaseDateModifiedEntity {

    private final Long courseId;
    private final String title;
    private final String description;
    private final String path;

    public Lecture(Long courseId, String title, String description,
                   String path) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.path = path;
    }

    public Lecture(Long id, Long courseId, String title,
                   String description, String path, LocalDateTime dateCreated,
                   LocalDateTime dateModified) {
        super(id, dateCreated, dateModified);
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.path = path;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public void recordTime() {
        this.recordDateCreated();
        this.recordDateModified();
    }

    public Lecture withId(long id) {
        return new Lecture(
                id,
                this.getCourseId(),
                this.getTitle(),
                this.getDescription(),
                this.getPath(),
                this.getDateCreated(),
                this.getDateModified()
        );
    }

}
