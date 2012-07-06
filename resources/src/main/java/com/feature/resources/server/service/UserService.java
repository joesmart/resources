package com.feature.resources.server.service;

import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.util.DomainObjectFactory;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 上午9:48
 * FileName:UserService
 */
public interface UserService {
    boolean registerUser(UserDTO userDTO);

    User findUserByLoginName(String loginName);

    boolean isUserAlreadyExists(String loginName);

    void updateDomainObjectFactory(DomainObjectFactory domainObjectFactory);
}
