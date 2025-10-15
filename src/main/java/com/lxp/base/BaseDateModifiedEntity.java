package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateModifiedEntity extends BaseDateCreatedEntity {
    private LocalDateTime dateModified;

    protected BaseDateModifiedEntity(Long id) {
        super(id);
        this.dateModified = this.getDateCreated();
    }
    
    public void recordDateModified() {
        this.dateModified = LocalDateTime.now();
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }
}
