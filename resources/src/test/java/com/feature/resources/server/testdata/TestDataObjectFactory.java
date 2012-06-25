package com.feature.resources.server.testdata;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.domain.Properties;
import com.feature.resources.server.service.WorkSpaceService;
import com.google.common.io.Resources;
import com.google.inject.Singleton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
}
