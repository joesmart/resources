package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.resources.testdata.TestDataObjectFactory;
import com.feature.resources.server.service.GraphicService;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import lombok.Data;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 下午3:16
 * FileName:MockGraphicServiceProvider
 */
@Data
public class MockGraphicServiceProvider implements Provider<GraphicService> {

    private GraphicService graphicService;
    TestDataObjectFactory testDataObjectFactory;

    @Inject
    public MockGraphicServiceProvider(@Named("testData") TestDataObjectFactory testDataObjectFactory) throws IOException {
        this.testDataObjectFactory = testDataObjectFactory;
        graphicService = mock(GraphicService.class);
        byte [] abc = new byte[10];
        Graphic graphic = testDataObjectFactory.getGraphic();
        ObjectId id = new ObjectId();
        Graphic afterSaveGraphic = cloneNewGraphicObjct(graphic, id);
        String idString = id.toString();
        URL url=  Resources.getResource("com/feature/resources/server/resources/graphics.png");
        byte[] bytes = Resources.toByteArray(url);
        when(graphicService.saveGraphic(bytes, graphic)).thenReturn(afterSaveGraphic);
        when(graphicService.get(idString)).thenReturn(afterSaveGraphic);
    }

    private Graphic cloneNewGraphicObjct(Graphic graphic, ObjectId id) {
        Graphic afterSaveGraphic  = new Graphic();
        afterSaveGraphic.setId(id);
        afterSaveGraphic.setName(graphic.getName());
        afterSaveGraphic.setType(graphic.getType());
        afterSaveGraphic.setId(id);
        afterSaveGraphic.setThumbnailPath("xxx");
        afterSaveGraphic.setOriginalFilePath("xxx");
        return afterSaveGraphic;
    }

    @Override
    public GraphicService get() {
        return graphicService;
    }
}
