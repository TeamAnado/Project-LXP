package com.lxp.lecture.model;

import com.lxp.base.BaseDateModifiedEntity;

public class Lecture extends BaseDateModifiedEntity {
    private final Long sectionId;
    private final String title;
    private final String description;

    private Lecture(Long sectionId, String title, String description) {
        this.sectionId = sectionId;
        this.title = title;
        this.description = description;
    }

    public static Lecture of(Long sectionId, String title, String description) {
        return new Lecture(sectionId, title, description);
    }

    public Long getSectionId() {
        return sectionId;
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
