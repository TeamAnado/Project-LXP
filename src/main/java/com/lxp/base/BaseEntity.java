package com.lxp.base;

public abstract class BaseEntity {
    private Long id;

    protected BaseEntity() {
    }

    protected BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
