package com.feature.resources.server.dao;

import com.feature.resources.server.domain.*;
import com.google.code.morphia.query.Query;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午5:12
 * FileName:GraphicDaoTest
 */
public class GraphicDaoTest extends BasicMongoUnitTest {
    private GraphicDao graphicDao;

    @Before
    public void setUp() throws IOException {
        tearDown();
        String[] strings = {"WorkSpace", "TagDescription", "Properties", "Graphic"};
        for (String string : strings) {
            initData(string);
        }
        graphicDao = new GraphicDao(getDatastore());
    }

    @After
    public void tearDown() {
        Query<Graphic> query = getDatastore().createQuery(Graphic.class);
        getDatastore().delete(query);
        Query<Properties> propertiesQuery = getDatastore().createQuery(Properties.class);
        getDatastore().delete(propertiesQuery);
        Query<TagDescription> tagDescriptionQuery = getDatastore().createQuery(TagDescription.class);
        getDatastore().delete(tagDescriptionQuery);
        Query<WorkSpace> workSpaceQuery = getDatastore().createQuery(WorkSpace.class);
        getDatastore().delete(workSpaceQuery);
    }

    @Test
    public void testFindAllByCreateAtTime() throws Exception {
        List<Graphic> graphicList = graphicDao.findAllByCreateAtTime();
        assertThat(graphicList).isNotNull();
        assertThat(graphicList.size()).isEqualTo(resourceDataSize("Graphic"));
        Date maxDate = graphicList.get(0).getCreateDate();
        Date secondDate = graphicList.get(1).getCreateDate();
        assertThat(maxDate.after(secondDate)).isTrue();
    }

    @Test
    public void testFindByPage() throws Exception {
        List<Graphic> graphics =  graphicDao.findByPage(1,10);
        assertThat(graphics).isNotNull();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(graphics.size()).isEqualTo(graphicSize>=10?10:graphicSize);
    }

    @Test
    public void testGetTotalRecordCount() throws Exception {
        long  totalRecordCount  = graphicDao.getTotalRecordCount();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(totalRecordCount).isEqualTo(graphicSize);
    }

    @Test
    public void should_get_checked_graphic_when_it_checked(){
        final  String statusDesc = CheckStatusDesc.CHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 10, CheckStatusDesc.CHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size());
        for(Graphic graphic:graphics){
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        }
    }

    @Test
    public void should_get_checked_graphic_when_it_checked_and_pageSizeIs_less_than_7(){
        final  String statusDesc = CheckStatusDesc.CHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 4, CheckStatusDesc.CHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size()<4?checkedList.size():4);
        for(Graphic graphic:graphics){
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        }
    }

    @Test
    public void should_get_unchecked_graphic_when_it_is_unchecked(){
        final  String statusDesc = CheckStatusDesc.UNCHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 4, CheckStatusDesc.UNCHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size()<4?checkedList.size():4);
        for(Graphic graphic:graphics){
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.UNCHECKED.getValue());
        }
    }

    @Test
    public void should_get_page_graphic_when_it_is_all(){
        final  String statusDesc = CheckStatusDesc.ALL.getValue();
        List<String> graphicList = getResourceStringList("Graphic");
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 4, CheckStatusDesc.ALL);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(graphicList.size() < 4 ? graphicList.size() : 4);
    }
}
