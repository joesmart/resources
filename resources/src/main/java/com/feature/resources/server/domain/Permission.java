package com.feature.resources.server.domain;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午4:02
 * FileName:Permission
 */
public class Permission extends ResourceEntity{
    public Permission(){
       setType("Permission");
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
