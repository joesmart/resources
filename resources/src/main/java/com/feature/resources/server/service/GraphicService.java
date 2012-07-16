package com.feature.resources.server.service;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.dto.GraphicCheckDTO;
import com.feature.resources.server.dto.GraphicDTO;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public  interface GraphicService {

     Graphic addNewGraphic(Graphic graphic, InputStream inputStream);

     Graphic add(Graphic graphic);

     void delete(String id);

     Graphic get(String id);

     List<Graphic> findAll();

     void writeThumbnailStreamIntoDisplay(String graphicId, OutputStream outputStream);

     void displayThumbnailImage(String graphicId, OutputStream outputStream, List<Integer> size);

     void writeOriginalResourceIntoOutputStream(String graphicId, OutputStream outputStream);

     Graphic saveGraphic(byte[] contents, Graphic graphic);

     List<Graphic> findGraphicByPage(int requestpage,int  pageSize);

     long getGraphicsTotalCount(String userId);

     void updateGraphic(GraphicDTO graphicDTO);

     String dealUploadDataToCreateNewGraphic(byte[] contents, Graphic graphic);

     Graphic generateGraphic(String fileName, long size, String contentType,String tagId,String workspaceId);

     List<Graphic> findGraphicByPageAndQueryTypeAndUser(int requestPage, int pageSize, String queryType, String userId);

    void checkGraphics(GraphicCheckDTO graphicCheckDTO);

    void batchDelete(List<String> idString);
}
