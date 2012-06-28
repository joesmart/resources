package com.feature.resources.server.resources;

import com.feature.resources.server.resources.inmemory.RestTest;
import com.feature.resources.server.resources.rules.RestClient;
import com.google.inject.Inject;
import com.sun.jersey.api.client.WebResource;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-26
 * Time: 下午9:49
 * FileName:TestResourceTest
 */
public class TestResourceTest extends RestTest {


    @Inject
    @Rule
    public  RestClient restClient;
    @Test
    public void testSayHello() throws Exception {
        WebResource resource = restClient.resource();
        assertThat(resource).isNotNull();
        String string = resource.path("test").get(String.class);
        assertThat(string).isNotNull();
        assertThat(string).isEqualTo("hello");
    }
}
