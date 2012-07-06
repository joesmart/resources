package com.feature.resources.server.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午3:05
 * FileName:TagResourceTest
 */
public class TagResourceTest extends BasicJerseyTest {

    @Before
    public void setUp() {
        client().addFilter(new HTTPBasicAuthFilter("joesmart","123456"));
        resource = resource();
    }

    @Test
    public void should_add_new_TagDescription_successful() {
        String json = "{\"tag\":\"test\"}";
        ClientResponse response = resource.path("tag/add").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_add_exist_TagDescription_fail() {
        String json = "{\"tag\":\"xxx\"}";
        ClientResponse response = resource.path("tag/add").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_MODIFIED.getStatusCode());
    }
    @Test
    public void should_get_tagDTO_list(){
        List<Map> tagDTOList = resource.path("tag/all").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).get(List.class);
        assertThat(tagDTOList).isNotNull();
        assertThat(tagDTOList.size()).isEqualTo(1);
        assertThat(tagDTOList.get(0).get("tag")).isEqualTo("mock");
    }
}
