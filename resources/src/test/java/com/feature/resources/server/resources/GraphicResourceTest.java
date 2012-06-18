package com.feature.resources.server.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
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
    public void shuld_get_all_graphic_pageinfo() throws Exception {
        String pageInfo = resource.path("graphics").path("pageinfo").get(String.class);
        LOGGER.info("unit-test:" + pageInfo);
        assertThat(pageInfo).isNotNull();
        assertThat(pageInfo).isEqualTo("{\"totalPage\":10,\"pageSize\":10}");
    }

    @Test
    public void should_request_a_graphics_page_successful(){
        String dataJson = resource.path("graphics").path("page").queryParam("requestPage","1").queryParam("pageSize","10").get(String.class);
        LOGGER.info(dataJson);
        assertThat(dataJson).isNotNull();
        assertThat(dataJson.contains("\"dataSize\":10")).isTrue();
    }

    @Test
    public  void should_delete_graphic_successful(){
        Form form = new Form();
        form.add("id","xxxxxx");
        ClientResponse response = resource.path("graphics").path("delete").post(ClientResponse.class,form);
        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
    }
}
