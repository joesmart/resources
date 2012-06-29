package com.feature.resources.server.domain;

import com.feature.resources.server.dto.FileMeta;

import java.util.UUID;

/**
 * User: ZouYanjian
 * Date: 12-6-5
 * Time: 上午11:09
 * FileName:DomainObjectFactory
 */
public class DomainObjectFactory {
    public Graphic createGraphic(String fileName, String contentType) {
        Graphic graphic = new Graphic();
        graphic.setType(contentType);
        graphic.setName(fileName);
        return graphic;
    }

    public Properties createProperties(String fileName, long size, String contentType) {
        Properties properties = new Properties();
        properties.setName(fileName);
        properties.setSize(size);
        properties.setUuid(UUID.randomUUID().toString());
        properties.setMimeType(contentType);
        properties.setStatus("unchecked");
        return properties;
    }

    public FileMeta createFileMeta(Graphic graphic) {
        String url = "../" + graphic.getOriginalFilePath();
        String thumbnailUrl = "../" + graphic.getThumbnailPath();
        String deleteUrl = "../rs/file/" + graphic.getId();
        long fileSize = graphic.getProperties().getSize();
        String fileName = graphic.getProperties().getName();
        return new FileMeta(fileName, fileSize, url, thumbnailUrl, deleteUrl);
    }
}
