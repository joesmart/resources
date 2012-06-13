package com.feature.resources.server.resources.guice;

import com.feature.resources.server.domain.DomainObjectFactory;
import com.feature.resources.server.resources.testdata.TestDataObjectFactory;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import lombok.Data;
import org.mockito.Mockito;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 下午3:32
 * FileName:MockDoaminObjectFactoryProvider
 */
@Data
public class MockDoaminObjectFactoryProvider implements Provider<DomainObjectFactory> {
    DomainObjectFactory domainObjectFactory;
    @Inject @Named("abc")
    private String abc;
    private  TestDataObjectFactory testDataObjectFactory;

    @Inject
    public MockDoaminObjectFactoryProvider(@Named("testData")TestDataObjectFactory testDataObjectFactory) {
        this.testDataObjectFactory = testDataObjectFactory;
        domainObjectFactory = Mockito.mock(DomainObjectFactory.class);
        Mockito.when(domainObjectFactory.createGraphic(Mockito.anyString(), Mockito.anyString())).thenReturn(testDataObjectFactory.getGraphic());
        Mockito.when(domainObjectFactory.createProperties(Mockito.anyString(), Mockito.anyLong(), Mockito.anyString())).thenReturn(testDataObjectFactory.getProperties());
    }

    @Override
    public DomainObjectFactory get() {
        return domainObjectFactory;
    }
}
