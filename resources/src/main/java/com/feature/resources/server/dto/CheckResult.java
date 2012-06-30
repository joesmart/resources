package com.feature.resources.server.dto;

/**
 * User: ZouYanjian
 * Date: 12-6-30
 * Time: 下午3:42
 * FileName:CheckResult
 */
public enum CheckResult {
    PASS,
    UNPASS;

    public String getValue(){
        return  this.toString();
    }
}
