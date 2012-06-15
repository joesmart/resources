package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.GraphicDao;
import com.feature.resources.server.dao.PropertiesDao;
import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.service.GraphicService;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.mongodb.gridfs.GridFSDBFile;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class GraphicServiceImpl implements GraphicService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicServiceImpl.class);

    @Inject
    GraphicDao graphicDao;
    @Inject
    PropertiesDao propertiesDao;

    private Integer width = 40;
    private Integer height = 40;

    public Graphic addNewGraphic(Graphic graphic, InputStream inputStream) {
        Preconditions.checkNotNull(graphic);
        Preconditions.checkNotNull(inputStream);
        try {
            Object fileId = graphicDao.create(graphic.getName(), "application/image", inputStream);
            graphic.setAttachment(fileId);
        } catch (IOException e) {
            LOGGER.error("errors:", e);
        }
        graphicDao.save(graphic);
        return graphic;
    }


    public void delete(String stringId) {
        ObjectId id = new ObjectId(stringId);
        checkParaments(stringId);
        Graphic graphic = get(stringId);
        propertiesDao.delete(graphic.getProperties());
        graphicDao.deleteGridFSDBFile(graphic.getAttachment());
        graphicDao.deleteById(id);
    }

    public Graphic get(String id) {
        checkParaments(id);
        return graphicDao.findOne("id", new ObjectId(id));
    }

    public List<Graphic> findAll() {
        List<Graphic> graphicList = graphicDao.findAllByCreateAtTime();
        return graphicList;
    }

    public Graphic add(Graphic graphic) {
        graphicDao.save(graphic);
        return graphic;
    }

    public void writeThumbnailStreamIntoDisplay(String graphicId, OutputStream outputStream) {
        checkParaments(graphicId);
        Graphic graphic = graphicDao.get(new ObjectId(graphicId));
        GridFSDBFile gridFSDBFile = graphicDao.getGridFSDBFile(graphic.getAttachment());

        if (gridFSDBFile != null) {
            try {
                Thumbnails.of(gridFSDBFile.getInputStream()).size(width, height).toOutputStream(outputStream);
            } catch (IOException e) {
                LOGGER.error("Thumbnail image error!",e);
                try {
                    displayOriginalImageContent(outputStream, gridFSDBFile);
                } catch (IOException e1) {
                    LOGGER.error("Original image read error!",e1);
                }finally {
                    closeIOStream(outputStream,null);
                }
            }
        }
    }


    public void displayThumbnailImage(String graphicId, OutputStream outputStream, List<Integer> size) {
        width = size.get(0);
        height = size.get(1);
        writeThumbnailStreamIntoDisplay(graphicId, outputStream);
    }


    public void writeOriginalResourceIntoOutputStream(String graphicId, OutputStream outputStream) {
        checkParaments(graphicId);
        Graphic graphic = graphicDao.get(new ObjectId(graphicId));
        if (graphic == null) {
            return;
        }
        GridFSDBFile gridFSDBFile = graphicDao.getGridFSDBFile(graphic.getAttachment());
        InputStream input = null;
        if (gridFSDBFile != null) {
            try {
                displayOriginalImageContent(outputStream, gridFSDBFile);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeIOStream(outputStream, input);
            }
        }
    }

    private void checkParaments(String graphicId) {
        Preconditions.checkNotNull(graphicId);
        Preconditions.checkArgument(StringUtils.isNotEmpty(graphicId));
    }

    private void displayOriginalImageContent(OutputStream outputStream, GridFSDBFile gridFSDBFile) throws IOException {
        byte[] btyes = new byte[2048];
        InputStream input = gridFSDBFile.getInputStream();
        int result = 0;
        while ((result = input.read(btyes)) != -1) {
            outputStream.write(btyes, 0, result);
        }
        outputStream.flush();
    }

    private void closeIOStream(OutputStream outputStream, InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                LOGGER.error("IO errors:", e);
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("IO errors:",e);
            }
        }
    }

    public Graphic saveGraphic(byte[] contents, Graphic graphic) {
        InputStream inputStream = new ByteArrayInputStream(contents);
        return  addNewGraphic(graphic, inputStream);
    }

    @Override
    public List<Graphic> findGraphicByPage(int requestpage, int pageSize) {
        List<Graphic> graphics =  graphicDao.findByPage(requestpage,pageSize);
        return graphics;
    }

    @Override
    public long getGraphicsTotalCount() {
        return graphicDao.getTotalRecordCount();
    }
}
