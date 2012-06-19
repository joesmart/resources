package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import lombok.Data;

@Entity
@Data
public class TagDescription extends ResourceEntity {
    private String tag;
}
