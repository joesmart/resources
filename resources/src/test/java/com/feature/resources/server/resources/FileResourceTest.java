package com.feature.resources.server.resources;

import com.feature.resources.server.service.GraphicService;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.google.inject.Inject;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.test.framework.AppDescriptor;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import static org.fest.assertions.api.Assertions.assertThat;

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
    @Inject
    private GraphicService graphicService;

    public FileResourceTest() {
        super();
    }

    @Override
    protected AppDescriptor configure() {
        return super.configure();
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
        final InputStream inputStream = testDataObjectFactory.getTestGraphicResource();
        FormDataMultiPart mp = new FormDataMultiPart();
        FormDataBodyPart p = new FormDataBodyPart(FormDataContentDisposition.name("part").build(), "CONTENT");
        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file").fileName("graphics.png").size(1000L).build(), inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        mp.bodyPart(p);
        mp.bodyPart(inputStreamBody);
        client().addFilter(new HTTPBasicAuthFilter("joesmart","123456"));
        WebResource webResource = resource().path("file");
        webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(mp);
    }

    @Test
    public void should_upload_graphic_with_workSpace_and_tag_info_successful_when_workspace_tag_file_not_null(){

        final InputStream inputStream = testDataObjectFactory.getTestGraphicResource();
        FormDataMultiPart mp = new FormDataMultiPart();
        FormDataBodyPart part = new FormDataBodyPart(FormDataContentDisposition.name("part").build(), "CONTENT");
        mp.bodyPart(part);

        FormDataBodyPart tag = new FormDataBodyPart(FormDataContentDisposition.name("tag").build(),"tagId");
        mp.bodyPart(tag);

        FormDataBodyPart workSpace = new FormDataBodyPart(FormDataContentDisposition.name("workSpace").build(),"workspaceId");
        mp.bodyPart(workSpace);

        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file").fileName("graphics.png").size(1000L).build(),
                                                                    inputStream,
                                                                    MediaType.APPLICATION_OCTET_STREAM_TYPE);

        mp.bodyPart(inputStreamBody);
        client().addFilter(new HTTPBasicAuthFilter("joesmart","123456"));
        WebResource webResource = resource().path("file");
        webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(mp);

    }

    @Test
    public void should_post_tag_and_workspace_info_success() {
        final InputStream inputStream = testDataObjectFactory.getTestGraphicResource();
        FormDataMultiPart mp = new FormDataMultiPart();
        FormDataBodyPart tag = new FormDataBodyPart(FormDataContentDisposition.name("tag").build(), "xxxx");
        FormDataBodyPart workSpace = new FormDataBodyPart(FormDataContentDisposition.name("workSpace").build(), "123456");
        FormDataBodyPart inputStreamBody = new FormDataBodyPart(FormDataContentDisposition.name("file").fileName("graphics.png").size(1000L).build(), inputStream, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        mp.bodyPart(tag);
        mp.bodyPart(workSpace);
        mp.bodyPart(inputStreamBody);
        WebResource webResource = resource().path("file");
        webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(mp);
    }


}
