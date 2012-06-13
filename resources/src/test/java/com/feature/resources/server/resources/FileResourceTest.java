package com.feature.resources.server.resources;

import com.feature.resources.server.resources.testdata.TestDataObjectFactory;
import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.test.framework.AppDescriptor;
import org.jukito.JukitoRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 上午10:23
 * FileName:FileResourceTest
 */
@RunWith(value = JukitoRunner.class)
public class FileResourceTest extends BasicJerseyTest {

    @Inject
    private TestDataObjectFactory testDataObjectFactory;

    public FileResourceTest() {
        super();
    }

    @Override
    protected AppDescriptor configure() {
        return super.configure();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testHead() throws Exception {
        WebResource webResource = resource();
        ClientResponse response = webResource.path("file").head();
        assertThat(response).isNotNull();
        assertThat(response).isNotNull();
        String entity = response.getEntity(String.class);
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }


    @Test
    public void testGet() {
        WebResource webResource = resource();
        String string = webResource.path("/file/url").get(String.class);
        assertThat(string).isNotNull();
    }

    @Test
    public void testPost() {
        InputStream inputStream = null;
        inputStream = testDataObjectFactory.getTestGraphicResource(inputStream);
        FormDataMultiPart mp = new FormDataMultiPart();
        FormDataBodyPart p = new FormDataBodyPart(FormDataContentDisposition.name("part").build(), "CONTENT");
        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file").fileName("graphics.png").size(1000L).build(), inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        mp.bodyPart(p);
        mp.bodyPart(inputStreamBody);
        WebResource webResource = resource().path("file");
        webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(mp);

    }

}
