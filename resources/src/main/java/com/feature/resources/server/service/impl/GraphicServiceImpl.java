package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.GraphicDao;
import com.feature.resources.server.dao.PropertiesDao;
import com.feature.resources.server.domain.*;
import com.feature.resources.server.dto.CheckResult;
import com.feature.resources.server.dto.CheckStatusDesc;
import com.feature.resources.server.dto.GraphicCheckDTO;
import com.feature.resources.server.dto.GraphicDTO;
import com.feature.resources.server.service.*;
import com.feature.resources.server.util.DomainObjectFactory;
import com.feature.resources.server.util.FilesUtil;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.mongodb.gridfs.GridFSDBFile;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class GraphicServiceImpl implements GraphicService {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicServiceImpl.class);

    @Inject
    private GraphicDao graphicDao;
    @Inject
    private PropertiesDao propertiesDao;
    @Inject
    private DomainObjectFactory objectFactory;
    @Inject
    private PropertiesService propertiesService;
    @Inject
    private TagService tagService;
    @Inject
    private WorkSpaceService workSpaceService;
    @Inject
    private CXServerService cxServerService;

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
        checkParameters(stringId);
        ObjectId id = new ObjectId(stringId);
        checkParameters(stringId);
        Graphic graphic = get(stringId);
        propertiesDao.delete(graphic.getProperties());
        graphicDao.deleteGridFSDBFile(graphic.getAttachment());
        graphicDao.deleteById(id);
    }

    public Graphic get(String id) {
        checkParameters(id);
        return graphicDao.findOne("id", new ObjectId(id));
    }

    public List<Graphic> findAll() {
        return graphicDao.findAllByCreateAtTime();
    }

    public Graphic add(Graphic graphic) {
        graphicDao.save(graphic);
        return graphic;
    }

    public void writeThumbnailStreamIntoDisplay(String graphicId, OutputStream outputStream) {
        checkParameters(graphicId);
        Graphic graphic = graphicDao.get(new ObjectId(graphicId));
        GridFSDBFile gridFSDBFile = graphicDao.getGridFSDBFile(graphic.getAttachment());
        InputStream inputStream = gridFSDBFile.getInputStream();
        if (gridFSDBFile != null) {
            try {
                BufferedImage image = findSupportImage(inputStream);
                if(image != null){

                    Thumbnails.of(image).size(width, height).imageType(BufferedImage.TYPE_INT_ARGB).outputFormat("PNG").toOutputStream(outputStream);
                    FileOutputStream fileOutputStream = FilesUtil.createTemplateThumbnailFile(graphicId, width, height);
                    Thumbnails.of(image).size(width, height).imageType(BufferedImage.TYPE_INT_ARGB).outputFormat("PNG").toOutputStream(fileOutputStream);
                }else {
                    displayOriginalImageContent(outputStream, inputStream);
                }
//                Thumbnails.of(inputStream).size(width, height).imageType(BufferedImage.TYPE_INT_ARGB).toOutputStream(outputStream);
            } catch (IOException e) {
                LOGGER.error("Thumbnail image error!",e);
            }finally {
                closeIOStream(outputStream,inputStream);
            }
        }
    }

    public BufferedImage findSupportImage(InputStream inputStream){
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            BufferedImage image = null;
            ImageReader imageReader;
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
            while (imageReaders!=null&&imageReaders.hasNext()){
                imageReader = imageReaders.next();
                imageReader.setInput(imageInputStream);
                try {
                    image = imageReader.read(0);
                    break;
                } catch (IOException e) {
                }
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public void displayThumbnailImage(String graphicId, OutputStream outputStream, List<Integer> size) {
        width = size.get(0);
        height = size.get(1);
        if(FilesUtil.isThumbnailFileAlreadyExists(graphicId,width,height)){
            try {
                FilesUtil.writeTempThumbnailFile(graphicId,width,height,outputStream);
            } catch (IOException e) {
               LOGGER.error("Read template thumbnail files failed",e);
            }
        }else {
            writeThumbnailStreamIntoDisplay(graphicId, outputStream);
        }
    }


    public void writeOriginalResourceIntoOutputStream(String graphicId, OutputStream outputStream) {
        checkParameters(graphicId);
        Graphic graphic = graphicDao.get(new ObjectId(graphicId));
        if (graphic == null) {
            return;
        }
        GridFSDBFile gridFSDBFile = graphicDao.getGridFSDBFile(graphic.getAttachment());
        InputStream input = gridFSDBFile.getInputStream();
        if (gridFSDBFile != null) {
            try {
                displayOriginalImageContent(outputStream, input);
            } catch (IOException e) {
                LOGGER.error("Display Image error:", e);
            } finally {
                closeIOStream(outputStream, input);
            }
        }
    }

    private void checkParameters(String graphicId) {
        Preconditions.checkNotNull(graphicId);
        Preconditions.checkArgument(StringUtils.isNotEmpty(graphicId));
        Preconditions.checkArgument(ObjectId.isValid(graphicId),"Graphic ID is invalid");
    }

    private void displayOriginalImageContent(OutputStream outputStream, InputStream inputStream) throws IOException {
        byte[] bytes = new byte[2048];
        InputStream input = inputStream;
        int result = 0;
        while ((result = input.read(bytes)) != -1) {
            outputStream.write(bytes, 0, result);
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
    public List<Graphic> findGraphicByPage(int requestPage, int pageSize) {
        return graphicDao.findByPage(requestPage,pageSize);
    }

    @Override
    public long getGraphicsTotalCount(String userId) {
        return graphicDao.getTotalRecordCount(userId);
    }

    @Override
    public long getGraphicsTotalCountByUserAndQueryType(String userId,String queryType) {
        String upperCaseQueryType = queryType.toUpperCase();
        CheckStatusDesc checkStatusDesc = CheckStatusDesc.valueOf(upperCaseQueryType);
        return graphicDao.countRecordByQueryType(userId,checkStatusDesc);
    }

    @Override
    public void updateGraphic(GraphicDTO graphicDTO) {
        TagDescription tagDescription = tagService.getTagDescriptionById(graphicDTO.getTagId());

        Graphic graphic  = get(graphicDTO.getId());
        graphic.setName(graphicDTO.getName());
        graphic.setDescription(graphicDTO.getDescription());
        if(tagDescription != null){
            graphic.setTag(tagDescription);
        }
        graphicDao.save(graphic);
    }

    public String dealUploadDataToCreateNewGraphic(byte[] contents, Graphic graphic) {
        Graphic afterSaveGraphic = saveGraphic(contents, graphic);
        return afterSaveGraphic.getIdString();
    }

    public Graphic generateGraphic(String fileName, long size, String contentType,String tagId,String workspaceId) {
        Graphic graphic = objectFactory.createGraphic(fileName, contentType);
        if(StringUtils.isNotEmpty(tagId) && ObjectId.isValid(tagId)){
            TagDescription tagDescription = tagService.getTagDescriptionById(tagId);
            graphic.setTag(tagDescription);
        }

        if(StringUtils.isNotEmpty(workspaceId) && ObjectId.isValid(workspaceId)){
            WorkSpace workSpace = workSpaceService.getWorkSpaceById(workspaceId);
            graphic.setWorkSpace(workSpace);
        }

        Properties properties = objectFactory.createProperties(fileName, size, contentType);
        propertiesService.addNewProperties(properties);
        graphic.setProperties(properties);


        return graphic;
    }

    @Override
//    @RequiresPermissions(value ={"user:xxx"} )
//    @RequiresAuthentication
    public List<Graphic> findGraphicByPageAndQueryTypeAndUser(int requestPage, int pageSize, String queryType, String userId) {
        String upperCaseQueryType = queryType.toUpperCase();
        return graphicDao.findByPageAndQueryTypeAndUserId(requestPage,pageSize, CheckStatusDesc.valueOf(upperCaseQueryType),userId);
    }

    @Override
    public void checkGraphics(GraphicCheckDTO graphicCheckDTO) {
        CheckResult checkResult = CheckResult.valueOf(graphicCheckDTO.getCheckResult());
        int row =graphicDao.updateCheckStatus(graphicCheckDTO.getGraphicIds(),CheckStatusDesc.CHECKED, checkResult);
        LOGGER.info("Check status updated row:"+row);
        cxServerService.updateGraphicResourceAuditStatus(graphicCheckDTO);
    }

    @Override
    public void batchDelete(List<String> idString) {
        graphicDao.batchDelete(idString);
    }
}
