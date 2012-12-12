package com.feature.resources.server.util;

import org.fest.assertions.Assertions;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: yanjianzou
 * Date: 12/12/12
 * Time: 2:26 PM
 */
public class FilesUtilTest {
    @Test
    public void testReadImageFileIntoBytes() throws URISyntaxException {
        URL url = this.getClass().getResource("error.jpg");

        File file = new File(url.toURI());

        System.out.println(Integer.MAX_VALUE);
        byte[] bytes = FilesUtil.readAllBytes(file);
        assertThat(bytes).isNotNull();
        assertThat(bytes.length+0L).isEqualTo(file.length());
    }
}
