package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;

import java.util.Date;


@Entity
public class Properties extends BaseEntity {
    private String uuid;
    private String name;
    private String path;
    private Long size;
    private Date create;
    private Date modified;
    private String mimeType;
    private String status;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Properties that = (Properties) o;

        if (!create.equals(that.create)) return false;
        if (!mimeType.equals(that.mimeType)) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (!name.equals(that.name)) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (!size.equals(that.size)) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (!uuid.equals(that.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + size.hashCode();
        result = 31 * result + create.hashCode();
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + mimeType.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
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
