package com.feature.resources.server.domain;

import lombok.Data;
@Data
public class ResourceEntity extends BaseEntity {

  private String type;

  public ResourceEntity() {
       type = "Base";
  }
}
