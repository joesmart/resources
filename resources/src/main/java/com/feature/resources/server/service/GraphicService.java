package com.feature.resources.server.service;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.dto.GraphicDTO;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface GraphicService {

    public Graphic addNewGraphic(Graphic graphic, InputStream inputStream);

    public Graphic add(Graphic graphic);

    public void delete(String id);

    public Graphic get(String id);

    public List<Graphic> findAll();

    public void writeThumbnailStreamIntoDisplay(String graphicId, OutputStream outputStream);

    public void displayThumbnailImage(String graphicId, OutputStream outputStream, List<Integer> size);

    public void writeOriginalResourceIntoOutputStream(String graphicId, OutputStream outputStream);

    public Graphic saveGraphic(byte[] contents, Graphic graphic);

    public List<Graphic> findGraphicByPage(int requestpage,int  pageSize);

    public long getGraphicsTotalCount();

    public void updateGraphic(GraphicDTO graphicDTO);

    public String dealUploadDataToCreateNewGraphic(byte[] contents, Graphic graphic);

    public Graphic generateGraphic(String fileName, long size, String contentType,String tagId,String workspaceId);
}
