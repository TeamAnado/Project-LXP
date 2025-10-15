package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateCreatedEntity {
    private LocalDateTime dateCreated;

    public void recordCreationTime() {
        this.dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
}
