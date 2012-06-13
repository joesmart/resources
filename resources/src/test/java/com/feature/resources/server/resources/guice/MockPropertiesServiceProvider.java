package com.feature.resources.server.resources.guice;

import com.feature.resources.server.service.PropertiesService;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import lombok.Data;
import org.mockito.Mockito;

/**
 * User: ZouYanjian
 * Date: 12-6-11
 * Time: 下午3:30
 * FileName:MockPropertiesServiceProvider
 */
@Data
@Singleton
public class MockPropertiesServiceProvider implements Provider<PropertiesService> {
    PropertiesService propertiesService = Mockito.mock(PropertiesService.class);
    @Override
    public PropertiesService get() {
        return propertiesService;
    }
}
