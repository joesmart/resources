package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.PrePersist;
import com.google.code.morphia.annotations.Transient;
import com.google.code.morphia.annotations.Version;
import org.bson.types.ObjectId;

import java.util.Date;


public class BaseEntity {
    @Id
    private ObjectId id;
    @Transient
    private String idString;
    private Date updateDate;
    private Date createDate;
    @Version
    private Long version;

    public BaseEntity(){
        createDate = new Date();
    }

    @PrePersist
    public void prePersist() {
        this.updateDate = new Date();
    }

    public String getIdString() {
        return id.toString();
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
