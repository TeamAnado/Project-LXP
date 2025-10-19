package com.lxp.course.model;

import com.lxp.global.base.BaseDateModifiedEntity;
import com.lxp.course.model.enums.Category;

import java.time.LocalDateTime;

public class Course extends BaseDateModifiedEntity {

    private Long instructorId;
    private String title;
    private Category category;
    private String description;

    /**
     * Constructor for reading from database
     * @param id
     * @param title
     * @param description
     * @param instructorId
     * @param category
     * @param dateCreated
     * @param dateModified
     */
    public Course(
            Long id, String title, String description, Long instructorId, Category category,
            LocalDateTime dateCreated, LocalDateTime dateModified
    ) {
        super(id, dateCreated, dateModified);
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.category = category;
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

