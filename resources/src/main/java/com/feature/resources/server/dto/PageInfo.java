package com.feature.resources.server.dto;

import lombok.Data;

@Data
public class PageInfo {
    public PageInfo(int totalRecord, int pageSize) {
        this.totalPage = totalRecord;
        this.pageSize = pageSize;
    }

    private int totalPage;
    private int pageSize;
}
