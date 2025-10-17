package com.lxp.lecture.model;

import com.lxp.global.base.BaseDateModifiedEntity;

public class Lecture extends BaseDateModifiedEntity {

    private final Long courseId;
    private final String title;
    private final String description;
    private final String path;

    private Lecture(Long courseId, String title, String description, String path) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.path = path;
    }

    public static Lecture of(Long courseId, String title, String description, String path) {
        return new Lecture(courseId, title, description, path);
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

}
