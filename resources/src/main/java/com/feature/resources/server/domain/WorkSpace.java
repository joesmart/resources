package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.common.base.Objects;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午2:50
 * FileName:WorkSpace
 */
@Entity
public class WorkSpace extends ResourceEntity {
    private String name;
    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id",getId().toString()).add("name",name).toString();
    }
}
