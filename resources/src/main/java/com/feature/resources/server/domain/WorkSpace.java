package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import lombok.Data;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午2:50
 * FileName:WorkSpace
 */
@Entity
@Data
public class WorkSpace extends ResourceEntity {
    private String name;
}
