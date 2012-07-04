package com.feature.resources.server.config.shiro;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * User: ZouYanjian
 * Date: 12-7-2
 * Time: 下午4:08
 * FileName:ShiroBaseRealm
 */
public class ShiroBaseRealm extends AuthorizingRealm{


    private CachingSecurityManager securityManager;

    private  HashedCredentialsMatcher matcher;

    @Inject
    public ShiroBaseRealm(CachingSecurityManager cachingSecurityManager){
        securityManager = cachingSecurityManager;
        /*matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);*/
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if(principals == null){
            throw new AuthorizationException("PrincipalCollection can't be null");
        }
        String userName = (String) principals.fromRealm(getName()).iterator().next();
        Set roleNames = ImmutableSet.of();
        if(StringUtils.isNotEmpty(userName)){
            roleNames = ImmutableSet.of("user", "admin","normal");
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.addStringPermission("user:xxx");

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        if(StringUtils.isEmpty(userName)){
            throw new IllegalStateException("UserName is Null");
        }
        String password = "password";
        return new SimpleAuthenticationInfo(userName,password,this.getName());
    }
}
