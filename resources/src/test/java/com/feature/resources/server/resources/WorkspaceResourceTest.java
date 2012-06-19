package com.feature.resources.server.resources;

import com.feature.resources.server.dto.WorkspaceDTO;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 上午11:10
 * FileName:WorkspaceResourceTest
 */
public class WorkspaceResourceTest extends BasicJerseyTest {
    @Inject
    private TestDataObjectFactory testDataObjectFactory;

    @Before
    public void setUp() {
        resource = resource();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void should_add_a_new_Workspace_successful() throws Exception {
        String json = "{\"name\":\"xxx\"}";

        ClientResponse response = resource.path("workspace/add").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    public void should_add_exists_WorkSpace_fail() {
        WorkspaceDTO dto = new WorkspaceDTO();
        dto.setName("test");
        ClientResponse response = resource.path("workspace/add").accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, dto);
        Assertions.assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_MODIFIED.getStatusCode());
    }
}
