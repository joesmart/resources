package com.feature.resources.server.domain;

/**
 * User: ZouYanjian
 * Date: 12-6-28
 * Time: 上午11:02
 * FileName:CheckStatusDesc
 */
public enum CheckStatusDesc {
    CHECED,
    UNCHECKED;

    public String getValue(){
        return  this.toString();
    }
}
