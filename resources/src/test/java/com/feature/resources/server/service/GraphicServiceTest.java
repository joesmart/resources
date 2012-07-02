package com.feature.resources.server.service;

import com.feature.resources.server.dao.GraphicDao;
import com.feature.resources.server.dao.PropertiesDao;
import com.feature.resources.server.domain.*;
import com.feature.resources.server.dto.CheckResult;
import com.feature.resources.server.dto.CheckStatusDesc;
import com.feature.resources.server.dto.GraphicCheckDTO;
import com.feature.resources.server.dto.GraphicDTO;
import com.feature.resources.server.service.impl.GraphicServiceImpl;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.code.morphia.Datastore;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Scopes;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.types.ObjectId;
import org.fest.assertions.Assertions;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
    TestDataObjectFactory testDataObjectFactory;

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
            bind(DomainObjectFactory.class).in(TestSingleton.class);
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

    @Test
    public void should_return_all_Graphic_when_queryType_is_all(GraphicDao graphicDao){
        CheckStatusDesc checkStatusDesc = CheckStatusDesc.UNCHECKED;
        List<Graphic> uncheckedList = testDataObjectFactory.createGraphicList(checkStatusDesc,3);
        List<Graphic> checkedList = testDataObjectFactory.createGraphicList(CheckStatusDesc.CHECKED,3);

        uncheckedList.addAll(checkedList);


        when(graphicDao.findByPageAndQueryType(1, 10, CheckStatusDesc.ALL)).thenReturn(uncheckedList);
        List<Graphic> pageGraphic = graphicService.findGraphicByPageAndQueryType(1,10,"ALL");
        Assertions.assertThat(pageGraphic).isNotNull();
        Assertions.assertThat(pageGraphic.size()).isEqualTo(uncheckedList.size());
    }

    @Test
    public void should_return_all_Graphic_when_queryType_is_checked(GraphicDao graphicDao){
        List<Graphic> checkedList = testDataObjectFactory.createGraphicList(CheckStatusDesc.CHECKED,3);
        when(graphicDao.findByPageAndQueryType(1, 10, CheckStatusDesc.CHECKED)).thenReturn(checkedList);
        List<Graphic> pageGraphic = graphicService.findGraphicByPageAndQueryType(1,10,CheckStatusDesc.CHECKED.getValue());
        Assertions.assertThat(pageGraphic).isNotNull();
        Assertions.assertThat(pageGraphic.size()).isEqualTo(checkedList.size()<10?checkedList.size():10);
    }

    @Test
    public void should_return_all_Graphic_when_queryType_is_unchecked(GraphicDao graphicDao){
        List<Graphic> uncheckedList = testDataObjectFactory.createGraphicList(CheckStatusDesc.UNCHECKED,3);
        when(graphicDao.findByPageAndQueryType(1, 10, CheckStatusDesc.UNCHECKED)).thenReturn(uncheckedList);
        List<Graphic> pageGraphic = graphicService.findGraphicByPageAndQueryType(1,10,CheckStatusDesc.UNCHECKED.getValue());
        Assertions.assertThat(pageGraphic).isNotNull();
        Assertions.assertThat(pageGraphic.size()).isEqualTo(uncheckedList.size()<10?uncheckedList.size():10);
    }

    @Test
    public void should_call_graphicDao_updateCheckStatus_method_when_check_graphics(GraphicDao graphicDao){
        GraphicCheckDTO graphicCheckDTO = new GraphicCheckDTO();
        graphicCheckDTO.setCheckResult("pass");
        List<String> graphicIDList = Lists.newArrayList();
        for(int i=0;i<10;i++){
            graphicIDList.add(new ObjectId().toString());
        }
        graphicCheckDTO.setGraphicIds(graphicIDList);
        CheckResult checkResult = CheckResult.valueOf(graphicCheckDTO.getCheckResult());
        when(graphicDao.updateCheckStatus(graphicIDList, CheckStatusDesc.CHECKED, checkResult)).thenReturn(10);
        graphicService.checkGraphics(graphicCheckDTO);
        verify(graphicDao).updateCheckStatus(graphicIDList,CheckStatusDesc.CHECKED, checkResult);
    }

    @Test
    public void should_get_total_count_graphics_successful(GraphicDao graphicDao){
        when(graphicDao.getTotalRecordCount()).thenReturn(10L);
        long totalCount = graphicService.getGraphicsTotalCount();
        assertThat(totalCount).isEqualTo(10L);
    }


    @Test
    public void should_update_graphic_successful(GraphicDao graphicDao,TagService tagService){
        graphic = testDataObjectFactory.getGraphic();
        graphic.setId(new ObjectId());

        GraphicDTO graphicDTO = new GraphicDTO();
        graphicDTO.setId(graphic.getIdString());
        graphicDTO.setTagId("abcd");

        TagDescription tagDescription = testDataObjectFactory.createTagDescription("test");
        when(tagService.getTagDescriptionById("abcd")).thenReturn(tagDescription);
        when(graphicService.get(graphic.getIdString())).thenReturn(graphic);
        graphicService.updateGraphic(graphicDTO);
        verify(graphicDao).save(graphic);
        assertThat(graphic.getTag()).isEqualTo(tagDescription);
    }

    @Test
    public void should_dealUploadDataToCreateNewGraphic_successful(GraphicDao graphicDao) throws IOException {
        graphic = testDataObjectFactory.getGraphic();
        URL url=  Resources.getResource("com/feature/resources/server/resources/graphics.png");
        byte[] bytes = Resources.toByteArray(url);
        final Graphic afterSaveGraphic = testDataObjectFactory.getGraphic();
        afterSaveGraphic.setId(new ObjectId());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Graphic temGraphic = (Graphic) invocation.getArguments()[0];
                temGraphic.setId(afterSaveGraphic.getId());
                return null;
            }
        }).when(graphicDao).save(graphic);
        String grpahicId = graphicService.dealUploadDataToCreateNewGraphic(bytes,graphic);
        assertThat(grpahicId).isEqualTo(afterSaveGraphic.getIdString());
        verify(graphicDao).save(graphic);
    }

    @Test
    public void should_generateGraphic_successful(GraphicDao graphicDao,DomainObjectFactory domainObjectFactory,
                                                  TagService tagService,WorkSpaceService workSpaceService,
                                                  PropertiesService propertiesService ){
        Graphic tempGraphic = domainObjectFactory.createGraphic("abc.png","image/png") ;
        TagDescription tagDescription = testDataObjectFactory.createTagDescription("tag");
        when(tagService.getTagDescriptionById("abcd")).thenReturn(tagDescription);

        WorkSpace workSpace = testDataObjectFactory.createWorkSpace("default");
        when(workSpaceService.getWorkSpaceById("default")).thenReturn(workSpace);

        Properties properties = domainObjectFactory.createProperties("abc.png", 100L, "image/png");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Properties properties1 = (Properties) invocation.getArguments()[0];
                return properties1;
            }
        }).when(propertiesService).addNewProperties(properties);
        Graphic graphic1 = graphicService.generateGraphic("abc.png",100L,"image/png","abcd","default");
        tempGraphic.setTag(tagDescription);
        tempGraphic.setProperties(properties);
        tempGraphic.setWorkSpace(workSpace);
        assertThat(graphic1.getTag()).isEqualTo(tempGraphic.getTag());
    }

    @Test(expected = NullPointerException.class)
    public void batchDeleteGraphic(GraphicDao graphicDao){
        when(graphicDao.batchDelete(null)).thenThrow(NullPointerException.class);

        graphicService.batchDelete(null);
        Assert.fail("Shouldn't go to here!");
    }

    @Test
    public void should_batchDeleteGraphic_successful(GraphicDao graphicDao){
        when(graphicDao.batchDelete(any(List.class))).thenReturn(10);
        graphicService.batchDelete(Lists.newArrayList("asfasdfasdf"));
        verify(graphicDao).batchDelete(any(List.class));
    }
}
