package com.feature.resources.server.domain;

public class ResourceEntity extends BaseEntity {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public ResourceEntity() {
        type = "Base";
    }

}
