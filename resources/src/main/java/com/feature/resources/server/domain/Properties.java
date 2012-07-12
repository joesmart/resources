package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;

import java.util.Date;


@Entity
public class Properties extends ResourceEntity {
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private Date create;
    private Date modified;
    private String mimeType;
    private String status;

    public Properties(){
       setType("Properties");
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }



    @Override
    public String toString() {
        return "Properties{" +
                "create=" + create +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", modified=" + modified +
                ", mimeType='" + mimeType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
