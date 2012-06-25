package com.feature.resources.server.dto;

public class PageInfo {
    public PageInfo(int totalRecord, int pageSize) {
        this.totalPage = totalRecord;
        this.pageSize = pageSize;
    }

    private int totalPage;
    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
