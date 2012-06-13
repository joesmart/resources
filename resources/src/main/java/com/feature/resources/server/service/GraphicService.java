package com.feature.resources.server.service;

import com.feature.resources.server.domain.Graphic;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface GraphicService {

  public Graphic addNewGraphic(Graphic graphic, InputStream inputStream);

  public Graphic add(Graphic graphic);

  public void delete(String id);

  public Graphic get(String id);

  // For requestFactory
  public List<Graphic> findAll();

  // For Rest
  public void writeThumbnailStreamIntoDisplay(String graphicId, OutputStream outputStream);

  // For Rest Serivces;
  public void displayThumbnailImage(String graphicId, OutputStream outputStream, List<Integer> size);
  
  public void writeOriginalResourceIntoOutputStream(String graphicId, OutputStream outputStream);

  public Graphic saveGraphic(byte[] contents, Graphic graphic);
}
