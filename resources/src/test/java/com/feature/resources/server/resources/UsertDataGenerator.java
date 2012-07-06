package com.feature.resources.server.resources;

import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.util.DomainObjectFactory;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ZouYanjian
 * Date: 12-7-6
 * Time: 上午10:38
 * FileName:UsertDataGenerator
 */
public class UsertDataGenerator {
    public static final Logger LOGGER = LoggerFactory.getLogger(UsertDataGenerator.class);
    @Test
    public void test(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("abcd@test.com");
        userDTO.setLoginName("joesmart");
        userDTO.setName("joesmart");
        userDTO.setPassword("123456");

        DomainObjectFactory domainObjectFactory = new DomainObjectFactory();
        User user = domainObjectFactory.translateToUser(userDTO);
        user.setId(new ObjectId());
        LOGGER.info(user.toString());
    }

}
