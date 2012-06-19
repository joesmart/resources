package com.feature.resources.server.service;

import com.feature.resources.server.dto.TagDTO;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午5:06
 * FileName:TagService
 */
public interface TagService {
    public void addNewTag(TagDTO tagDTO);

    public boolean exists(String tag);
}
