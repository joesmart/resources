package com.feature.resources.server.dto;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 上午10:22
 * FileName:UserStatus
 */
public enum UserStatus {
    ACTIVE,
    NONACTIVE;

    public String getValue(){
        return  this.toString();
    }
}
