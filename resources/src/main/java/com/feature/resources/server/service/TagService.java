package com.feature.resources.server.service;

import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.dto.TagDTO;

import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午5:06
 * FileName:TagService
 */
public interface TagService {
    void addNewTag(TagDTO tagDTO);

    boolean exists(String tag, String userId);

    List<TagDTO> getCurrentTagList();

    List<TagDTO> getCurrentTagListByUserId(String userId);

    TagDescription getTagDescriptionById(String id);
}
