package com.feature.resources.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataListInfo<T> {
    private String name;
    private int dataSize;
    private List<T> dataList;
}
