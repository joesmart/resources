package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午2:50
 * FileName:WorkSpace
 */
@Entity
public class WorkSpace extends ResourceEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
