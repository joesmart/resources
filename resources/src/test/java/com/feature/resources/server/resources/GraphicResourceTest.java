package com.feature.resources.server.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feature.resources.server.dto.GraphicCheckDTO;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-13
 * Time: 上午10:52
 * FileName:GraphicResourceTest
 */
public class GraphicResourceTest extends BasicJerseyTest {

    public GraphicResourceTest() {
        super();
    }

    @Test
    public void shuld_get_all_graphic_pageinfo() throws Exception {
        String pageInfo = resource.path("graphics").path("pageinfo").get(String.class);
        LOGGER.info("unit-test:" + pageInfo);
        assertThat(pageInfo).isNotNull();
        assertThat(pageInfo).isEqualTo("{\"totalPage\":10,\"pageSize\":10}");
    }

    @Test
    public void should_request_a_graphics_page_successful() {
        String dataJson = resource.path("graphics").path("page").queryParam("requestPage", "1").queryParam("pageSize", "10").queryParam("queryType", "all").get(String.class);
        LOGGER.info(dataJson);
        assertThat(dataJson).isNotNull();
        assertThat(dataJson.contains("\"dataSize\":10")).isTrue();
    }

    @Test
    public void should_delete_graphic_successful() {
        Form form = new Form();
        form.add("id", "xxxxxx");
        ClientResponse response = resource.path("graphics").path("delete").post(ClientResponse.class, form);
        assertThat(response.getStatus()).isEqualTo(ClientResponse.Status.OK.getStatusCode());
    }

    @Test
    public void should_check_graphic_successful() throws IOException {
        GraphicCheckDTO graphicCheckDTO = new GraphicCheckDTO();
        graphicCheckDTO.setCheckResult("checked");
        graphicCheckDTO.setGraphicIds(Lists.newArrayList("asdfasdfasdf"));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(graphicCheckDTO);

        ClientResponse response = resource.path("graphics").path("checks").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_batch_delete_graphic_successful() throws IOException {
        List<String> idString = Lists.newArrayList("a","b","c");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(idString);

        ClientResponse response = resource.path("graphics").path("batch_delete").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }
}
