package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Version;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;


@Data
public class BaseEntity {
    @Id
    private ObjectId id;
    private Date updateDate;
    @Version
    private Long version;

    @PrePersist
    public void prepersist() {
        this.updateDate = new Date();
    }
}
