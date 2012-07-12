package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Length;

@Entity
public class Graphic extends ResourceEntity {
    private String name;
    @Length(max = 60)
    private String description;
    private String thumbnailPath;
    private String originalFilePath;
    @Reference
    private Properties properties;
    @Property("image_appachement")
    private Object attachment;
    @Reference
    private WorkSpace workSpace;
    private TagDescription tag;
    private String checkStatus;
    private String checkResult;
    private String userId;

    public  Graphic(){
        super.setType("Graphic");
    }

    @PostLoad
    public void updatePath(){
        String path = "rs/graphics?id=" + this.getId().toString();
        this.thumbnailPath=path + "&size=30X30";
        this.originalFilePath= path;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    public void setOriginalFilePath(String originalFilePath) {
        this.originalFilePath = originalFilePath;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public TagDescription getTag() {
        return tag;
    }

    public void setTag(TagDescription tag) {
        this.tag = tag;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public WorkSpace getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString(){
        return  Objects.toStringHelper(this)
                 .add("name", getName())
                .add("description", getDescription())
                .add("tag",getTag())
                .add("workspace",getWorkSpace())
                .add("checkStatus",getCheckStatus())
                .toString();
    }
}
