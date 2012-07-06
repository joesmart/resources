package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.service.UserService;
import com.feature.resources.server.service.impl.UserServiceImpl;
import com.feature.resources.server.util.DomainObjectFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.bson.types.ObjectId;
import org.mockito.Mockito;

import static org.mockito.Matchers.anyString;

/**
 * User: ZouYanjian
 * Date: 12-7-6
 * Time: 上午10:16
 * FileName:MockUserServiceProvider
 */
public class MockUserServiceProvider implements Provider<UserService> {
    private UserService userService;
    private DomainObjectFactory domainObjectFactory;

    @Inject
    public MockUserServiceProvider(DomainObjectFactory domainObjectFactory) {
        userService = Mockito.mock(UserServiceImpl.class);
        UserDTO userDTO = new UserDTO();
        userDTO.setName("joesmart");
        userDTO.setEmail("abcd@test.com");
        userDTO.setLoginName("joesmart");
        userDTO.setPassword("123456");
        User user = domainObjectFactory.translateToUser(userDTO);
        user.setId(new ObjectId("4ff687ae97acbe55b0b3591c"));
        user.setPassword("14833e7029bed30a7d54513d42818f9368626ac2");
        user.setSalt("e9f1c6b6ced0cf69");
        Mockito.when(userService.findUserByLoginName(anyString())).thenReturn(user);
    }

    @Override
    public UserService get() {
        return userService;
    }
}
