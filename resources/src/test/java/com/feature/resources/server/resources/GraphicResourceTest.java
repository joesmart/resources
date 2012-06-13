package com.feature.resources.server.resources;

import com.sun.jersey.api.client.WebResource;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-13
 * Time: 上午10:52
 * FileName:GraphicResourceTest
 */
public class GraphicResourceTest extends BasicJerseyTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(GraphicResourceTest.class);
    private WebResource resource;

    public GraphicResourceTest() {
        super();
    }

    @Before
    public  void setup() {
        resource = resource();
    }

    @Test
    public void testGetGraphicPageInfo() throws Exception {
        String pageInfo = resource.path("graphics").path("pageinfo").get(String.class);
        LOGGER.info("unit-test:" + pageInfo);
        assertThat(pageInfo).isNotNull();
        assertThat(pageInfo).isEqualTo("{\"totalRecord\":10,\"pageSize\":10}");
    }

    @Test
    public void testGetGraphicListByPage(){
        String dataJson = resource.path("graphics").path("page").queryParam("requestPage","1").queryParam("pageSize","10").get(String.class);
        LOGGER.info(dataJson);
        assertThat(dataJson).isNotNull();
        assertThat(dataJson).isEqualTo("{}");
    }
}
