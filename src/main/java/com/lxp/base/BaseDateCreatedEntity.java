package com.lxp.base;

import java.time.LocalDateTime;

public abstract class BaseDateCreatedEntity extends BaseEntity {
    private final LocalDateTime dateCreated;

    protected BaseDateCreatedEntity(Long id) {
        super(id);
        this.dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
}
