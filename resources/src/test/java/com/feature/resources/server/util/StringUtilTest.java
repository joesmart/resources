package com.feature.resources.server.util;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午8:25
 * FileName:StringUtilTest
 */
public class StringUtilTest {

    @Test
    public void testGetFileExtensionName() throws Exception {
        assertThat(StringUtil.getFileExtensionName("abcd.txt")).isEqualTo("txt");
    }

    @Test
    public void should_get_fileextension_fail() {
        try {
            StringUtil.getFileExtensionName("xxxxx.");
            Assert.fail("");
        } catch (Exception e) {

        }
    }

    @Test
    public void should_get_fileextension_fail2() {
        try {
            StringUtil.getFileExtensionName("xxxxx");
            Assert.fail("");
        } catch (Exception e) {
        }
    }

    @Test
    public void testStringSizeListConvertToIntSizeList() throws Exception {
        List<Integer> sizeList = StringUtil.stringSizeListConvertToIntSizeList(Lists.asList("60", new String[]{"60"}));
        assertThat(sizeList).isNotNull();
        assertThat(sizeList.get(0)).isEqualTo(60);
        assertThat(sizeList.get(1)).isEqualTo(60);
    }

    @Test
    public void should_return_default_size() {
        List<Integer> sizeList = StringUtil.stringSizeListConvertToIntSizeList(Lists.asList("ss", new String[]{"ss"}));
        assertThat(sizeList).isNotNull();
        assertThat(sizeList.get(0)).isEqualTo(40);
        assertThat(sizeList.get(1)).isEqualTo(40);
    }


    @Test(expected = NullPointerException.class)
    public void should_throw_a_nullException_when_cast_null_object(){
        Map map = new HashMap();
//        map.put("1",new Integer(1));
        int integer= (Integer)map.get("1");
        Assert.fail("Shouldn't get here!");
    }
}
