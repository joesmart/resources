package com.feature.resources.server.service;

import com.feature.resources.server.dao.PropertiesDao;
import com.feature.resources.server.util.DomainObjectFactory;
import com.feature.resources.server.domain.Properties;
import com.feature.resources.server.service.impl.PropertiesServiceImpl;
import com.google.code.morphia.Datastore;
import com.google.inject.Inject;
import com.google.inject.Scopes;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

/**
 * User: ZouYanjian
 * Date: 12-6-30
 * Time: 下午9:08
 * FileName:PropertiesServiceTest
 */
@RunWith(JukitoRunner.class)
public class PropertiesServiceTest {
    @Inject
    private PropertiesService propertiesService;
    public static class Module extends JukitoModule{

        @Override
        protected void configureTest() {
            bindMock(PropertiesDao.class).in(Scopes.SINGLETON);
            forceMock(Datastore.class);
            bind(DomainObjectFactory.class).in(TestSingleton.class);
            bind(PropertiesService.class).to(PropertiesServiceImpl.class).in(TestSingleton.class);
        }
    }
    @Test
    public void testAddNewProperties(PropertiesDao propertiesDao,DomainObjectFactory domainObjectFactory) throws Exception {
        Properties properties = domainObjectFactory.createProperties("text.png",100L,"image/png");
        propertiesService.addNewProperties(properties);
        Mockito.verify(propertiesDao).save(properties);
    }
}
