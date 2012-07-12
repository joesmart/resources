package com.feature.resources.server.service.impl;

import com.feature.resources.server.config.shiro.ShiroBaseRealm;
import com.feature.resources.server.dao.UserDao;
import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.service.UserService;
import com.feature.resources.server.util.DomainObjectFactory;
import com.google.code.morphia.Key;
import com.google.inject.Inject;

/**
 * User: ZouYanjian
 * Date: 12-7-5
 * Time: 上午9:55
 * FileName:UserServiceImpl
 */
public class UserServiceImpl implements UserService{
    @Inject
    private UserDao userDao;
    @Inject
    private DomainObjectFactory domainObjectFactory;

    @Inject(optional = true)
    private ShiroBaseRealm shiroBaseRealm;
    @Override
    public boolean registerUser(UserDTO userDTO) {
        User user = domainObjectFactory.translateToUser(userDTO);
        Key<User> key = userDao.save(user);
        if(shiroBaseRealm!=null && user!= null){
            shiroBaseRealm.clearCachedAuthorizationInfo(user.getIdString(),user.getLoginName(),user.getName());
        }
        return isKeyNotNUll(key);
    }

    @Override
    public User findUserByLoginName(String loginName) {
        return userDao.findUserByLoginName(loginName);
    }

    @Override
    public boolean isUserAlreadyExists(String loginName) {
        return  userDao.exists("loginName",loginName);
    }

    private boolean isKeyNotNUll(Key<User> key) {
        return (key != null && key.getId() != null) ? true:false;
    }

    public void updateDomainObjectFactory(DomainObjectFactory domainObjectFactory) {
        this.domainObjectFactory = domainObjectFactory;
    }
}
