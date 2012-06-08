package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.PostLoad;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
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

    public  Graphic(){
        super.setType("Graphic");
    }

    @PostLoad
    public void updatePath(){
        String path = "rs/graphics?id=" + this.getId().toString();
        this.thumbnailPath=path + "&size=80X80";
        this.originalFilePath= path;
    }
}
