package com.feature.resources.server.dto;

import lombok.Data;

@Data
public class PageInfo {
    public PageInfo(int totalRecord, int pageSize) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
    }

    private int totalRecord;
    private int pageSize;
}
