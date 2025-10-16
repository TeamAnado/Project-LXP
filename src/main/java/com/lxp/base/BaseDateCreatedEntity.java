package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateCreatedEntity extends BaseEntity {
    private LocalDateTime dateCreated;

    public void recordDateCreated() {
        this.dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
}
