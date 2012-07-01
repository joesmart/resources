package com.feature.resources.server.service.impl;

import com.feature.resources.server.dao.PropertiesDao;
import com.feature.resources.server.domain.Properties;
import com.feature.resources.server.service.PropertiesService;
import com.google.inject.Inject;

/**
 * User: ZouYanjian
 * Date: 12-6-5
 * Time: 上午10:41
 * FileName:PropertiesServiceImpl
 */
public class PropertiesServiceImpl implements PropertiesService {
    @Inject
    private PropertiesDao propertiesDao;
    @Override
    public void addNewProperties(Properties properties) {
        propertiesDao.save(properties);
    }
}
