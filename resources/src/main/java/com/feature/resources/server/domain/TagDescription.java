package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import lombok.Data;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午2:55
 * FileName:TagDescription
 */
@Entity
@Data
public class TagDescription extends ResourceEntity {
    private String tag;
}
