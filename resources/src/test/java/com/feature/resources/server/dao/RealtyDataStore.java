package com.feature.resources.server.dao;

import com.feature.resources.server.config.morphia.MorphiaGuiceModule;
import com.feature.resources.server.domain.Permission;
import com.feature.resources.server.domain.Role;
import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.util.DomainObjectFactory;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.inject.Inject;
import junit.framework.Assert;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * User: ZouYanjian
 * Date: 12-7-4
 * Time: 下午5:14
 * FileName:RealtyDataStore
 */
@RunWith(JukitoRunner.class)
public class RealtyDataStore {
    @Inject
    private PermissionDao permissionDao;
    @Inject
    private Datastore ds;
    @Inject
    private RoleDao roleDao;
    @Inject
    private DomainObjectFactory domainObjectFactory;

    @Inject
    private UserDao userDao;
    public static class Module extends JukitoModule{

        @Override
        protected void configureTest() {
            install(new MorphiaGuiceModule());
        }
    }
    @Test
    public void should_get_empty_data(){
        destroyAllData();
        Role role = new Role();
        role.setName("admin");

        Permission permission = new Permission();
        permission.setName("user:view");
        permissionDao.save(permission);
        role.addNewPermission(permission);

        Permission permission1 = new Permission();
        permission1.setName("user:edite");
        permissionDao.save(permission1);
        role.addNewPermission(permission);

        roleDao.save(role);

        UserDTO userDTO = new UserDTO();
        userDTO.setLoginName("joesmart");
        userDTO.setPassword("123456");
        userDTO.setEmail("abc@test.com");
        userDTO.setName("joesmart");
        User user = domainObjectFactory.translateToUser(userDTO);
        user.addNewRole(role);
        userDao.save(user);
        Assert.assertNotNull(permissionDao);
    }

    @Inject
    public void destroyAllData(){
        Query<User> query = ds.createQuery(User.class);
        ds.delete(query);
        Query<Role> role = ds.createQuery(Role.class);
        ds.delete(role);
        Query<Permission> permissions = ds.createQuery(Permission.class);
        ds.delete(permissions);
        Query<User> user = ds.createQuery(User.class);
        ds.delete(user);
    }
}
