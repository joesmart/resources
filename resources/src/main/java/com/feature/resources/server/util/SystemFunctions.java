package com.feature.resources.server.util;

import com.feature.resources.server.domain.TagDescription;
import com.feature.resources.server.domain.WorkSpace;
import com.feature.resources.server.dto.TagDTO;
import com.feature.resources.server.dto.WorkSpaceDTO;
import com.google.common.base.Function;
import org.bson.types.ObjectId;

import javax.annotation.Nullable;

/**
 * User: ZouYanjian
 * Date: 12-7-1
 * Time: 下午6:51
 * FileName:SystemFunctions
 */
public class SystemFunctions {


    public Function<String, ObjectId> convertIdStringToObjectId() {
        return new Function<String, ObjectId>() {
            @Override
            public ObjectId apply(@Nullable String input) {
                if (input == null || "".equals(input)) {
                    return new ObjectId();
                }
                return new ObjectId(input);
            }
        };
    }

    public Function<TagDescription, TagDTO> convertTagDescriptonToTagDTO() {
        return new Function<TagDescription, TagDTO>() {
            @Override
            public TagDTO apply(@Nullable TagDescription input) {
                TagDTO tagDTO  = new TagDTO();
                tagDTO.setId(input.getIdString());
                tagDTO.setTag(input.getTag());
                return tagDTO;
            }
        };
    }

    public Function<WorkSpace, WorkSpaceDTO> convertWorkSpaceToWorkSpaceDTO() {
        return new Function<WorkSpace, WorkSpaceDTO>() {
            @Override
            public WorkSpaceDTO apply(@Nullable WorkSpace input) {
                WorkSpaceDTO workSpaceDTO = new WorkSpaceDTO();
                workSpaceDTO.setId(input.getIdString());
                workSpaceDTO.setName(input.getName());
                return workSpaceDTO;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }
}
