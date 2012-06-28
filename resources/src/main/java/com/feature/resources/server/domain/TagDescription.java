package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.common.base.Objects;

@Entity
public class TagDescription extends ResourceEntity {
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",getId().toString()).add("tag",tag).toString();
    }

}
