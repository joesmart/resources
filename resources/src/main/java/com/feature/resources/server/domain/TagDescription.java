package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.common.base.Objects;

@Entity
public class TagDescription extends ResourceEntity {
    public TagDescription(){
       setType("TagDescription");
    }
    private String tag;
    private String userId;

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
