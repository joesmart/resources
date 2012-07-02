package com.feature.resources.server.util;

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
}
