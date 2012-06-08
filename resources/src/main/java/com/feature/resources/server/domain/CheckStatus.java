package com.feature.resources.server.domain;


import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;
import lombok.Data;

@Entity
@Data
public class CheckStatus extends BaseEntity{
    private String result;
    @Reference
    private User user;
}
