package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.TagDao;
import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.TagService;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * User: ZouYanjian
 * Date: 12-6-18
 * Time: 下午5:06
 * FileName:TagServiceImpl
 */
public class TagServiceImpl implements TagService {
    @Inject
    private TagDao tagDao;

    @Override
    public void addNewTag(TagDTO tagDTO) {
        Preconditions.checkNotNull(tagDTO);
        TagDescription tagDescription = new TagDescription();
        tagDescription.setTag(tagDTO.getTag());

        tagDao.save(tagDescription);
    }
}
