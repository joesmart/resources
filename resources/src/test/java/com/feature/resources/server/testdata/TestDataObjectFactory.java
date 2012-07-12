package com.feature.resources.server.testdata;

import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.CheckStatusDesc;
import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.domain.Properties;
import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.inject.Singleton;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 下午6:21
 * FileName:TestDataObjectFactory
 */
@Singleton
public class TestDataObjectFactory {
    private Graphic graphic;
    private Properties properties;
    private WorkSpaceService workSpaceService;

    public TestDataObjectFactory(){
        graphic = new Graphic();
        graphic.setName("test");
        graphic.setDescription("test");
        graphic.setType("png");

        properties = new Properties();
        properties.setName("test");
        properties.setSize(1000L);
        properties.setMimeType("png");
    }

    public InputStream getTestGraphicResource() {
        InputStream inputStream = null;
        try {
            URL url = Resources.getResource("com/feature/resources/server/resources/graphics.png");
            inputStream = Resources.newInputStreamSupplier(url).getInput() ;
            assertThat(inputStream).isNotNull();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public WorkSpaceService getWorkSpaceService() {
        return workSpaceService;
    }

    public void setWorkSpaceService(WorkSpaceService workSpaceService){
        this.workSpaceService = workSpaceService;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public TagDescription createTagDescription(String tag) {
        TagDescription tagDescription = new TagDescription();
        tagDescription.setId(new ObjectId());
        tagDescription.setTag(tag);
        return tagDescription;
    }

    public List<TagDescription> createTagDescriptionList(){
        return createTagDescriptionList(null);
    }

    public List<TagDescription> createTagDescriptionList(String userId){
        List<TagDescription> tagDescriptionList = Lists.newArrayList();
        for(Integer i:new Integer[]{1,2,3,4,5}){
            TagDescription tagDescription = createTagDescription(String.valueOf(i));
            tagDescription.setUserId(userId);
            tagDescriptionList.add(tagDescription);

        }
        return tagDescriptionList;
    }

    public Graphic createGraphicByCheckStatusDesc(CheckStatusDesc checkStatusDesc) {
        Graphic graphic = new Graphic();
        graphic.setId(new ObjectId());
        graphic.setName("hello");
        graphic.setCheckStatus(checkStatusDesc.getValue());
        graphic.setDescription("test");
        return graphic;
    }

    public List<Graphic> createGraphicList(CheckStatusDesc checkStatusDesc,int listSize){
        List<Graphic> graphics = Lists.newArrayList();
        Graphic graphic;
        for(int i=0;i<listSize;i++){
            graphic = createGraphicByCheckStatusDesc(checkStatusDesc);
            graphics.add(graphic);
        }

        return graphics;
    }

    public WorkSpace createWorkSpace(String name) {
        WorkSpace workSpace = new WorkSpace();
        workSpace.setId(new ObjectId());
        workSpace.setName(name);
        return  workSpace;
    }
}
