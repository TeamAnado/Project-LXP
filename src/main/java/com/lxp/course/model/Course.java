package com.lxp.course.model;

import com.lxp.base.BaseDateModifiedEntity;
import com.lxp.course.model.enums.Category;

import java.time.LocalDateTime;

public class Course extends BaseDateModifiedEntity {
    private String title;
    private String description;
    private Long instructorId;
    private Category category;

    // DB조회용 생성자
    public Course(
            Long id, String title, String description,
            LocalDateTime dateCreated, LocalDateTime dateModified,
            Long instructorId,Category category
    ) {
        setId(id);
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.category = category;
        setDateCreated(dateCreated);
        setDateModified(dateModified);
    }

    // 신규 생성용 생성자
    public Course(String title, String description, Long instructorId, Category category) {
        this.title = title;
        this.description = description;
        this.instructorId =instructorId;
        this.category = category;
        recordCreationTime();
        recordDateModified();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public Long getInstructorId() {
        return instructorId;
    }

    public Category getCategory() {
        return category;
    }
}

