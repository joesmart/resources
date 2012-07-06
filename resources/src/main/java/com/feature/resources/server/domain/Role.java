package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Reference;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午4:01
 * FileName:Role
 */
public class Role extends ResourceEntity {
    private String name;

    @Reference
    private List<Permission> permissions = Lists.newArrayList();

    public Role(){
       setType("Role");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNewPermission(Permission permission){
        permissions.add(permission);
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
