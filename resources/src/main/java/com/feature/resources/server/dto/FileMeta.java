package com.feature.resources.server.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FileMeta {
    private String resourceId;
    private String name;
    private long size;
    private String url;
    private String thumbnail_url;
    private String delete_url;
    private String delete_type;
    private CheckStatusDesc checkStatusDesc;

    public FileMeta(String fileName,long size,String url,String thumbnailUrl,String delete_url,String resourceId){
        this.name = fileName;
        this.size = size;
        this.url = url;
        this.thumbnail_url = thumbnailUrl;
        this.delete_url = delete_url;
        this.delete_type = "DELETE";
        this.resourceId = resourceId;
    }

    public FileMeta(){

    }

    public String getDelete_type() {
        return delete_type;
    }

    public void setDelete_type(String delete_type) {
        this.delete_type = delete_type;
    }

    public String getDelete_url() {
        return delete_url;
    }

    public void setDelete_url(String delete_url) {
        this.delete_url = delete_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public CheckStatusDesc getCheckStatusDesc() {
        return checkStatusDesc;
    }

    public void setCheckStatusDesc(CheckStatusDesc checkStatusDesc) {
        this.checkStatusDesc = checkStatusDesc;
    }
}
