package com.feature.resources.server.config.shiro;

import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserStatus;
import com.feature.resources.server.service.UserService;
import com.feature.resources.server.util.Encodes;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.io.Serializable;
import java.util.Set;

/**
 * User: ZouYanjian
 * Date: 12-7-2
 * Time: 下午4:08
 * FileName:ShiroBaseRealm
 */
public class ShiroBaseRealm extends AuthorizingRealm {
    private static final int INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    private static final String ALGORITHM = "SHA-1";

    private HashedCredentialsMatcher matcher;

    @Inject
    private UserService userService;

    @Inject
    public ShiroBaseRealm() {

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
        matcher.setHashIterations(INTERATIONS);

        setCredentialsMatcher(matcher);
        setAuthenticationCachingEnabled(true);
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection can't be null");
        }
        String userName = (String) principals.fromRealm(getName()).iterator().next();
        Set roleNames = ImmutableSet.of();
        if (StringUtils.isNotEmpty(userName)) {
            roleNames = ImmutableSet.of("user", "admin", "normal");
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.addStringPermission("user:xxx");

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        if (StringUtils.isEmpty(userName)) {
            throw new IllegalStateException("UserName is Null");
        }

        User user = userService.findUserByLoginName(userName);

        if (user != null) {
            if (UserStatus.NONACTIVE.getValue().equals(user.getStatus())) {
                throw new DisabledAccountException();
            }

            byte[] salt = Encodes.decodeHex(user.getSalt());
            ShiroUser shiroUser = new ShiroUser(user.getIdString(), user.getLoginName(), user.getName());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }

    /**
     * 更新用户授权信息缓存.
     */
    public void clearCachedAuthorizationInfo(String id,String principal,String name) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(new ShiroUser(id, principal, name), getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        private String loginName;
        private String name;
        private String userId;

        public ShiroUser(String userId, String loginName, String name) {
            this.loginName = loginName;
            this.name = name;
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("userId",userId)
                                                 .add("loginName", loginName)
                                                 .add("name", name)
                                                 .toString();
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, "loginName");
        }

        /**
         * 重载equals,只比较loginName
         */
        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj, "loginName");
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
