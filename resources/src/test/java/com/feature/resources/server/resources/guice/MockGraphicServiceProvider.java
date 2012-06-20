package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.feature.resources.server.service.GraphicService;
import com.google.common.collect.*;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import lombok.Data;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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
    public static final Logger LOGGER = LoggerFactory.getLogger(MockGraphicServiceProvider.class);

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

        List<Graphic> graphics = Lists.newArrayList();

        URL url=  Resources.getResource("com/feature/resources/server/resources/graphics.png");
        byte[] bytes = Resources.toByteArray(url);
        when(graphicService.saveGraphic(bytes, graphic)).thenReturn(afterSaveGraphic);
        when(graphicService.getGraphicsTotalCount()).thenReturn(100L);

        when(graphicService.get(idString)).thenReturn(afterSaveGraphic);
        generateGraphicListTestFixture(graphic, graphics);
        when(graphicService.findGraphicByPage(1,10)).thenReturn(graphics);
    }

    private void generateGraphicListTestFixture(Graphic graphic, List<Graphic> graphics) {
        ContiguousSet<Integer> set = Ranges.closedOpen(1, 11).asSet(DiscreteDomains.integers());
        LOGGER.info(set.toString()+"set size"+ set.size());
        for(Integer i:set){
            graphic.setId(new ObjectId());
            graphics.add(graphic);
        }
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
