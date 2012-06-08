package com.feature.resources.server.domain;

import com.google.code.morphia.annotations.Entity;
import lombok.Data;

@Entity
@Data
public class User extends ResourceEntity {
    private String name;
    private String password;

    public User(){
        setType("user");
    }

}
