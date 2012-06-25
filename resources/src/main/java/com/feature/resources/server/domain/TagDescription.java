package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;

@Entity
public class TagDescription extends ResourceEntity {
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
