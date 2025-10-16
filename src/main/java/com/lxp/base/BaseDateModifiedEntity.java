package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateModifiedEntity extends BaseDateCreatedEntity {
    private LocalDateTime dateModified;

    public BaseDateModifiedEntity() {
    }

    public BaseDateModifiedEntity(Long id, LocalDateTime dateCreated, LocalDateTime dateModified) {
        super(id, dateCreated);
        this.dateModified = dateModified;
    }

    public void recordDateModified() {
        this.dateModified = LocalDateTime.now();
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

}
