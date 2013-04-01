package com.feature.resources.server.domain;

import com.feature.resources.server.dto.CheckStatusDesc;
import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-28
 * Time: 上午10:44
 * FileName:GraphicTest
 */
public class GraphicTest {
    @Test
    public void testToString() throws Exception {

        Graphic graphic = new Graphic();
        graphic.setId(new ObjectId());
        graphic.setName("hello");
        graphic.setDescription("desc");
        TagDescription tagDescription = new TagDescription();
        tagDescription.setId(new ObjectId());
        tagDescription.setTag("action");
        graphic.setTag(tagDescription);

        WorkSpace workSpace = new WorkSpace();
        workSpace.setId(new ObjectId());
        workSpace.setName("test");
        graphic.setWorkSpace(workSpace);

        graphic.setCheckStatus(CheckStatusDesc.UNCHECKED.getValue());
        String graphiString = graphic.toString();
        System.out.println(graphiString);
        assertThat(graphiString).isNotNull();

        Assert.assertTrue("should contain hello", graphiString.contains("hello"));
        Assert.assertTrue("should contain desc", graphiString.contains("desc"));
        Assert.assertTrue("should contain action", graphiString.contains("action"));
        Assert.assertTrue("should contain test",graphiString.contains("test"));
        Assert.assertTrue("should contain hello",graphiString.contains("hello"));
        Assert.assertTrue("should contain hello",graphiString.contains("UNCHECKED"));
    }


}
