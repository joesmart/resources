package com.feature.resources.server.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class FileUrl {
    String url;
    public FileUrl(String url){
        this.url = url;
    }
    public FileUrl(){

    }

   /* public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }*/
}
