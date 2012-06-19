package com.feature.resources.server.resources;

import com.sun.jersey.api.client.ClientResponse;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午3:05
 * FileName:TagResourceTest
 */
public class TagResourceTest extends BasicJerseyTest {

    @Before
    public void setUp() {
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
}
