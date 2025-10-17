package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateCreatedEntity extends BaseEntity {
    private LocalDateTime dateCreated;

    protected BaseDateCreatedEntity() {
    }

    protected BaseDateCreatedEntity(Long id, LocalDateTime dateCreated) {
        super(id);
        this.dateCreated = dateCreated;
    }
    
    public void recordDateCreated() {
        this.dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

}
