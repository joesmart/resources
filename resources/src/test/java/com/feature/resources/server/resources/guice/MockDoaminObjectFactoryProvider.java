package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.dto.UserStatus;
import com.feature.resources.server.testdata.TestDataObjectFactory;
import com.feature.resources.server.util.DomainObjectFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.bson.types.ObjectId;
import org.mockito.Mockito;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 下午3:32
 * FileName:MockDoaminObjectFactoryProvider
 */
public class MockDoaminObjectFactoryProvider implements Provider<DomainObjectFactory> {
    DomainObjectFactory domainObjectFactory;
    @Inject @Named("abc")
    private String abc;
    private  TestDataObjectFactory testDataObjectFactory;
    private  User user;

    @Inject
    public MockDoaminObjectFactoryProvider(@Named("testData")TestDataObjectFactory testDataObjectFactory) {
        this.testDataObjectFactory = testDataObjectFactory;
        domainObjectFactory = Mockito.mock(DomainObjectFactory.class);
        createMockUser();
        when(domainObjectFactory.translateToUser(any(UserDTO.class))).thenReturn(user);
        when(domainObjectFactory.createGraphic(anyString(), anyString())).thenReturn(testDataObjectFactory.getGraphic());
        when(domainObjectFactory.createProperties(anyString(), anyLong(), anyString())).thenReturn(testDataObjectFactory.getProperties());
    }

    private void createMockUser() {
        user = new User();
        user.setId(new ObjectId("4ff6507e97ac4dfbbbc107f6"));
        user.setLoginName("joesmart");
        user.setName("joesmart");
        user.setPlainPassword("123456");
        user.setPassword("4223953fc3cd3ba50b45ac510f797f5e2f10de2b");
        user.setPassword("86b588a56a545edf");
        user.setEmail("abcd@test.com");
        user.setStatus(UserStatus.ACTIVE.getValue());
    }

    @Override
    public DomainObjectFactory get() {
        return domainObjectFactory;
    }
}
