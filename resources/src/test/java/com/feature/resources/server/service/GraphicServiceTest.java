package com.feature.resources.server.service;

import com.feature.resources.server.dao.GraphicDao;
import com.feature.resources.server.dao.PropertiesDao;
import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.service.impl.GraphicServiceImpl;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.code.morphia.Datastore;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Scopes;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * User: ZouYanjian
 * Date: 12-6-13
 * Time: 下午4:44
 * FileName:GraphicServiceTest
 */
@RunWith(JukitoRunner.class)
public class GraphicServiceTest {

    @Inject
    GraphicService graphicService;
    @Inject
    GraphicDao graphicDao;
    private Graphic graphic;
    private ObjectId objectId;
    private List<Graphic> graphics;
    private GridFSDBFile gridFSDBFile;
    private InputStream inputStream;


    public static  class  Module extends JukitoModule{
        @Override
        protected void configureTest() {
            bindMock(GraphicDao.class).in(TestSingleton.class);
            bindMock(PropertiesDao.class).in(Scopes.SINGLETON);
            forceMock(Datastore.class);
            forceMock(OutputStream.class);
            bind(TestDataObjectFactory.class).in(Scopes.SINGLETON);
            bind(GraphicService.class).to(GraphicServiceImpl.class);
        }
    }
    @Before
    public void setUp(GraphicDao graphicDao,TestDataObjectFactory testDataObjectFactory) throws Exception {
        graphics = Lists.newArrayList();
        graphic = testDataObjectFactory.getGraphic();
        for(int i=1 ;i<=10;i++){
            graphics.add(graphic);
        }
        objectId = new ObjectId();

        gridFSDBFile = mock(GridFSDBFile.class);
        inputStream = testDataObjectFactory.getTestGraphicResource();
        when(graphicDao.getGridFSDBFile(null)).thenReturn(gridFSDBFile);
        when(gridFSDBFile.getInputStream()).thenReturn(inputStream);
        byte[] bytes = new byte[2048];
        byte[] returnBytes = new byte[2048];
//        inputStream.read(returnBytes);
//        when(inputStream.read(bytes)).thenReturn(returnBytes.length).thenReturn(-1);

        Mockito.when(graphicDao.findByPage(1,10)).thenReturn(graphics);
        Mockito.when(graphicDao.findOne("id",objectId)).thenReturn(graphic);
        Mockito.when(graphicDao.get(objectId)).thenReturn(graphic);
    }

    @Test
    public void testAddNewGraphic() throws Exception {
        graphicService.add(graphic);
        verify(graphicDao).save(graphic);
    }

    @Test
    public void testDelete() throws Exception {
        graphicService.delete(objectId.toString());
        verify(graphicDao).deleteById(objectId);
    }

    @Test
    public void testGet() throws Exception {
        graphicService.get(objectId.toString());
        verify(graphicDao).findOne("id",objectId);
    }

    @Test
    public void testFindAll() throws Exception {
        graphicService.findAll();
        verify(graphicDao).findAllByCreateAtTime();
    }

    @Test
    public void testAdd(GraphicDao graphicDao) throws Exception {
        graphicService.add(graphic);
        verify(graphicDao).save(graphic);
    }

    @Test
    public void testWriteThumbnailStreamIntoDisplay(OutputStream outputStream) throws Exception {

        graphicService.writeOriginalResourceIntoOutputStream(objectId.toString(),outputStream);

        verify(graphicDao).get(objectId);
        verify(graphicDao).getGridFSDBFile(graphic.getAttachment());
    }

    @Test
    public void testDisplayThumbnailImage(TestDataObjectFactory testDataObjectFactory,OutputStream outputStream) throws Exception {
        List<Integer> integers = Lists.newArrayList(40,40);
        graphicService.displayThumbnailImage(objectId.toString(), outputStream, integers);
        verify(graphicDao).get(objectId);
        verify(graphicDao).getGridFSDBFile(graphic.getAttachment());
    }

    @Test
    public void testWriteOriginalResourceIntoOutputStream(OutputStream outputStream) throws Exception {

        graphicService.writeOriginalResourceIntoOutputStream(objectId.toString(), outputStream);

        verify(graphicDao).get(objectId);
        verify(graphicDao).getGridFSDBFile(graphic.getAttachment());
    }

    @Test
    public void testSaveGraphic() throws Exception {
        byte[] bytes = new byte[2048];
        graphicService.saveGraphic(bytes,graphic);
        verify(graphicDao).save(graphic);
    }

    @Test
    public void testFindGraphicByPage(GraphicDao graphicDao) throws Exception {
        List<Graphic> graphics = graphicService.findGraphicByPage(1,10);
        verify(graphicDao,only()).findByPage(1,10);
        assertThat(graphics.size()).isEqualTo(10);
    }
}
