package com.feature.resources.server.resources.testdata;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.domain.Properties;
import com.google.common.io.Resources;
import com.google.inject.Singleton;
import lombok.Data;

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
@Data
@Singleton
public class TestDataObjectFactory {
    private Graphic graphic;
    private Properties properties;

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

    public InputStream getTestGraphicResource(InputStream inputStream) {
        try {
            URL url = Resources.getResource("com/feature/resources/server/resources/graphics.png");
            inputStream = Resources.newInputStreamSupplier(url).getInput() ;
            assertThat(inputStream).isNotNull();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return inputStream;
    }
}
