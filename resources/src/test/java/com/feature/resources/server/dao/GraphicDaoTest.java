package com.feature.resources.server.dao;

import com.feature.resources.server.domain.CheckStatusDesc;
import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.query.Query;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    private GraphicDao graphicDao;

    @Before
    public void setUp() throws IOException {
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
        List<String> allGraphic = getResourceStringList("Graphic");
        List<String> checkedList = Lists.newArrayList(Iterables.filter(allGraphic,new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                boolean conditions =  input.contains("\"checkStatus\":\"CHECKED\"");
                return conditions;

            }
        }) );
        System.out.println(checkedList.size());
        List<Graphic> graphics = graphicDao.findByPageAndQueryType(1, 10, CheckStatusDesc.CHECED);
        Assertions.assertThat(graphics).isNotNull();
    }
}
