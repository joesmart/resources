package com.feature.resources.server.dao;

import com.feature.resources.server.domain.*;
import com.feature.resources.server.dto.CheckResult;
import com.feature.resources.server.dto.CheckStatusDesc;
import com.feature.resources.server.util.SystemFunctions;
import com.google.code.morphia.query.Query;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
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
    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicDaoTest.class);

    private GraphicDao graphicDao;

    @Before
    public void setUp() throws IOException {
        tearDown();
        String[] strings = {"WorkSpace", "TagDescription", "Properties", "Graphic"};
        for (String string : strings) {
            initData(string);
        }
        graphicDao = new GraphicDao(getDatastore());
        graphicDao.setFunctions(new SystemFunctions());
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
    public void should_FindAllByCreateAtTime_return_correct_order() throws Exception {
        List<Graphic> graphicList = graphicDao.findAllByCreateAtTime();
        assertThat(graphicList).isNotNull();
        assertThat(graphicList.size()).isEqualTo(resourceDataSize("Graphic"));
        Date maxDate = graphicList.get(0).getCreateDate();
        Date secondDate = graphicList.get(1).getCreateDate();
        assertThat(maxDate.after(secondDate)).isTrue();
    }

    @Test
    public void should_FindByPage_return_a_page() throws Exception {
        List<Graphic> graphics = graphicDao.findByPage(1, 10);
        assertThat(graphics).isNotNull();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(graphics.size()).isEqualTo(graphicSize >= 10 ? 10 : graphicSize);
    }

    @Test
    public void should_FindByPage_return_a_page_when_requestPage_is_zero() throws Exception {
        List<Graphic> graphics = graphicDao.findByPage(0, 10);
        assertThat(graphics).isNotNull();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(graphics.size()).isEqualTo(graphicSize >= 10 ? 10 : graphicSize);
        judgeGraphicsOrder(graphics);

    }

    private void judgeGraphicsOrder(List<Graphic> graphics) {
        int size = graphics.size();
        if(size > 1) {
            Date firstDate = graphics.get(0).getCreateDate();
            Date lastDate = graphics.get(size - 1).getCreateDate();
            if(!lastDate.equals(firstDate)){
                boolean dateIsOrder = firstDate.after(lastDate);
                assertThat(dateIsOrder).isEqualTo(true);
            }
        }
    }

    @Test
    public void should_GetTotalRecordCount_return_correct_size() throws Exception {
        long totalRecordCount = graphicDao.getTotalRecordCount();
        int graphicSize = resourceDataSize("Graphic");
        assertThat(totalRecordCount).isEqualTo(graphicSize);
    }

    @Test
    public void should_get_checked_graphic_when_it_checked() {
        final String statusDesc = CheckStatusDesc.CHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 10, CheckStatusDesc.CHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size());
        for (Graphic graphic : graphics) {
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        }
    }

    @Test
    public void should_get_checked_graphic_first_page_when_it_checked_and_request_page_is_zero() {
        final String statusDesc = CheckStatusDesc.CHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(0, 10, CheckStatusDesc.CHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size());
        for (Graphic graphic : graphics) {
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        }
        judgeGraphicsOrder(graphics);
    }

    @Test
    public void should_get_checked_graphic_when_it_checked_and_pageSizeIs_less_than_7() {
        final String statusDesc = CheckStatusDesc.CHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 4, CheckStatusDesc.CHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size() < 4 ? checkedList.size() : 4);
        for (Graphic graphic : graphics) {
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        }
        judgeGraphicsOrder(graphics);
    }

    @Test
    public void should_get_unchecked_graphic_when_it_queryType_is_unchecked() {
        final String statusDesc = CheckStatusDesc.UNCHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<String> nullCheckStatusList = getGraphicStringListByNullCheckStatus();
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 24, CheckStatusDesc.UNCHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isNotEqualTo(checkedList.size());
        assertThat(graphics.size()).isEqualTo(checkedList.size() + nullCheckStatusList.size() < 24 ? checkedList.size() + nullCheckStatusList.size() : 24);
        for (Graphic graphic : graphics) {
            if (graphic.getCheckStatus() != null && !graphic.getCheckStatus().equals(""))
                Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.UNCHECKED.getValue());
        }
        judgeGraphicsOrder(graphics);
    }

    @Test
    public void should_get_unchecked_graphic_when_it_queryType_is_latest(){
        final String statusDesc = CheckStatusDesc.LATEST.getValue();
        List<String> allGraphics = getResourceStringList("Graphic");
        graphicDao.setMinusDays(30);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 24, CheckStatusDesc.LATEST);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(allGraphics.size());
        judgeGraphicsOrder(graphics);
    }


    @Test
    public void should_get_unchecked_graphic_when_it_is_unchecked() {
        final String statusDesc = CheckStatusDesc.UNCHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 4, CheckStatusDesc.UNCHECKED);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(checkedList.size() < 4 ? checkedList.size() : 4);
        for (Graphic graphic : graphics) {
            if (graphic.getCheckStatus() != null && !graphic.getCheckStatus().equals(""))
                Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.UNCHECKED.getValue());
        }
        judgeGraphicsOrder(graphics);
    }

    @Test
    public void should_get_page_graphic_when_it_is_all() {
        final String statusDesc = CheckStatusDesc.ALL.getValue();
        List<String> graphicList = getResourceStringList("Graphic");
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 4, CheckStatusDesc.ALL);
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(graphicList.size() < 4 ? graphicList.size() : 4);
        judgeGraphicsOrder(graphics);
    }

    @Test
    public void should_update_graphic_check_status_successful() {
        final String statusDesc = CheckStatusDesc.UNCHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);

        ObjectId objectId = null;
        String checkStatus = null;
        if (checkedList.size() > 0) {
            String tempJson = checkedList.get(0);
            DBObject dbObject = (DBObject) JSON.parse(tempJson);
            objectId = (ObjectId) dbObject.get("_id");
            checkStatus = (String) dbObject.get("checkStatus");
        }

        List<String> ids = Lists.newArrayList(objectId.toString());
        int row = graphicDao.updateCheckStatus(ids, CheckStatusDesc.CHECKED, CheckResult.PASS);
        Graphic graphic = graphicDao.findOne("id", objectId);
        assertThat(graphic).isNotNull();
        assertThat(row).isEqualTo(1);
        assertThat(graphic.getIdString()).isEqualTo(objectId.toString());
        assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        assertThat(graphic.getCheckStatus()).isNotEqualTo(checkStatus);
    }

    @Test(expected = IllegalStateException.class)
    public void should_update_graphic_check_status_throw_exception_when_id_string_list_is_empty() {
        List<String> ids = Lists.newArrayList();
        int row = graphicDao.updateCheckStatus(ids, CheckStatusDesc.CHECKED, CheckResult.PASS);
        Assert.fail("shouldn't run to here");
    }

    @Test(expected = NullPointerException.class)
    public void should_update_graphic_check_status_throw_exception_when_id_string_list_is_null() {
        List<String> ids = null;
        int row = graphicDao.updateCheckStatus(ids, CheckStatusDesc.CHECKED, null);
        Assert.fail("shouldn't run to here");
    }

    @Test(expected = NullPointerException.class)
    public void should_update_graphic_check_status_throw_exception_when_CheckStatusDesc_is_null() {
        List<String> ids = Lists.newArrayList("xxxxx");
        int row = graphicDao.updateCheckStatus(ids, null, null);
        Assert.fail("shouldn't run to here");
    }

    @Test
    public void should_update_graphic_check_status_get_only_one_row_when_id_contain_null_or_empty_string() {
        final String statusDesc = CheckStatusDesc.UNCHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);

        ObjectId objectId = null;
        String checkStatus = null;
        if (checkedList.size() > 0) {
            String tempJson = checkedList.get(0);
            DBObject dbObject = (DBObject) JSON.parse(tempJson);
            objectId = (ObjectId) dbObject.get("_id");
            checkStatus = (String) dbObject.get("checkStatus");
        }

        List<String> ids = Lists.newArrayList(objectId.toString(), null, "");
        int row = graphicDao.updateCheckStatus(ids, CheckStatusDesc.CHECKED, CheckResult.PASS);
        assertThat(row).isEqualTo(1);

    }

    @Test
    public void should_delete_graphic_successful_when_idStringList_is_not_null(){
        final String statusDesc = CheckStatusDesc.UNCHECKED.getValue();
        List<String> graphicList = getResourceStringList("Graphic");
        ObjectId objectId = null;
        String checkStatus = null;
        List<String> idStringList = Lists.newArrayList();
        for(String tempJson:graphicList){
            DBObject dbObject = (DBObject) JSON.parse(tempJson);
            objectId = (ObjectId) dbObject.get("_id");
            idStringList.add(objectId.toString());
        }
        int row = graphicDao.batchDelete(idStringList);
        assertThat(row).isGreaterThan(0);
        assertThat(row).isEqualTo(idStringList.size());
    }


    @Test
    public void should_get_graphic_by_userId_when_query_by_user_and_query_type(){
        final String statusDesc = CheckStatusDesc.LATEST.getValue();
        List<String> allGraphics = getResourceStringList("Graphic");
        List<String> userGraphics = convertToUserContainGraphicList(allGraphics);
        graphicDao.setMinusDays(30);
        List<Graphic> graphics = graphicDao.findByPageAndQueryTypeAndUserId(1, 24, CheckStatusDesc.LATEST, "4ff410a897ac21319cf81011");
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(userGraphics.size());
        judgeGraphicsOrder(graphics);
    }

    private List<String> convertToUserContainGraphicList(List<String> allGraphics) {
        return Lists.newArrayList(Iterables.filter(allGraphics, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.contains("\"userId\":\"4ff410a897ac21319cf81011\"");
            }
        }));
    }

    @Test
    public void should_get_checked_graphic_when_it_checked_and_query_by_user() {
        final String statusDesc = CheckStatusDesc.CHECKED.getValue();
        List<String> checkedList = getGraphicStringListByCheckStatus(statusDesc);
        List<String> userGraphics = convertToUserContainGraphicList(checkedList);
        List<Graphic> graphics = graphicDao.findByPageAndQueryTypeAndUserId(1, 10, CheckStatusDesc.CHECKED,"4ff410a897ac21319cf81011");
        assertThat(graphics).isNotNull();
        assertThat(graphics.size()).isEqualTo(userGraphics.size());
        for (Graphic graphic : graphics) {
            Assertions.assertThat(graphic.getCheckStatus()).isEqualTo(CheckStatusDesc.CHECKED.getValue());
        }
    }
}
