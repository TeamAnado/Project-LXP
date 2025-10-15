package com.lxp.base;

public abstract class BaseEntity {
    private final Long id;

    protected BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
