package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateModifiedEntity extends BaseDateCreatedEntity {
    private LocalDateTime dateModified;

    public void recordDateModified() {
        this.dateModified = LocalDateTime.now();
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    protected void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }
}
