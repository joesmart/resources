package com.feature.resources.server.resources;

import com.feature.resources.server.config.shiro.ShiroBaseRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ZouYanjian
 * Date: 12-7-6
 * Time: 下午5:22
 * FileName:Resource
 */
public class Resource {
    public static final Logger LOGGER = LoggerFactory.getLogger(Resource.class);
    protected ShiroBaseRealm.ShiroUser shiroUser;

    protected void getCurrentUserFromUserssion() {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser != null){
            shiroUser = (ShiroBaseRealm.ShiroUser) currentUser.getPrincipal();
            LOGGER.info(shiroUser.toString());
        }
    }
}
