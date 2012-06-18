package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class Properties extends BaseEntity {
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private Date create;
    private Date modified;
    private String mimeType;
    private String status;

}
