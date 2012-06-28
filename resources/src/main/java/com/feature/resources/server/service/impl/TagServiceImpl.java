package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.TagDao;
import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.service.TagService;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

import javax.annotation.Nullable;
import java.util.List;

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

    @Override
    public boolean exists(String tag) {
        return  tagDao.isAlreadyExists("tag", tag);
    }

    @Override
    public List<TagDTO> getCurrentTagList() {
        List<TagDescription> tagDescriptions  = tagDao.getEntityList();
        List<TagDTO> tagDTOList = Lists.transform(tagDescriptions,new Function<TagDescription, TagDTO>() {
            @Override
            public TagDTO apply(@Nullable TagDescription input) {
                TagDTO tagDTO  = new TagDTO();
                tagDTO.setId(input.getIdString());
                tagDTO.setTag(input.getTag());
                return tagDTO;
            }
        });
        return tagDTOList;
    }

    @Override
    public TagDescription getTagDescriptionById(String id) {
        TagDescription tag = tagDao.findOne("id",new ObjectId(id) );
        return tag;
    }
}
