package com.lxp.lecture.model;

import com.lxp.base.BaseDateModifiedEntity;

public class Lecture extends BaseDateModifiedEntity {

    private final String title;
    private final String description;

    private Lecture(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static Lecture of(String title, String description) {
        return new Lecture(title, description);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void recordTime() {
        this.recordDateCreated();
        this.recordDateModified();
    }

}
