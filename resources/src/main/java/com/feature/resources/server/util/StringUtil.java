package com.feature.resources.server.util;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;

import java.security.InvalidParameterException;
import java.util.List;

public class StringUtil {
  public static String getFileExtensionName(String fileName) {

    CharMatcher charMatcher = CharMatcher.is('.');
    int nameLength = fileName.length();
    int index = charMatcher.lastIndexIn(fileName);

    if (index >= nameLength - 1) {
      throw new InvalidParameterException("传入的文件名不包含扩展名");
    }
    if (index < 0) {
      throw new InvalidParameterException("传入的文件名不包含扩展名");
    }
    String extentionSymbol = fileName.substring(charMatcher.lastIndexIn(fileName) + 1).toLowerCase();
    return extentionSymbol;
  }

  public static List<Integer> stringSizeListConvertToIntSizeList(List<String> sizeList) {
    List<Integer> list = Lists.newArrayList();
    boolean isCorrectList = true;
    CharMatcher charMatcher = CharMatcher.DIGIT;
    for (String temp : sizeList) {
      if (charMatcher.matchesNoneOf(temp)) {
        isCorrectList = false;
        break;
      } else {
        list.add(Integer.parseInt(temp));
      }
    }

    if (!isCorrectList) {
      list.clear();
      list.add(40);
      list.add(40);
    }
    return list;
  }

}
