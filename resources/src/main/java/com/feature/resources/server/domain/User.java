package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;

@Entity
public class User extends ResourceEntity {
    private String name;
    private String password;

    public User(){
        setType("user");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
