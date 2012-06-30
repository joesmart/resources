package com.feature.resources.server.dto;

/**
 * User: ZouYanjian
 * Date: 12-6-28
 * Time: 上午11:02
 * FileName:CheckStatusDesc
 */
public enum CheckStatusDesc {
    ALL,
    CHECKED,
    UNCHECKED,
    LATEST;

    public String getValue(){
        return  this.toString();
    }
}
