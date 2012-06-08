package com.feature.resources.server.config.jersey;

import com.feature.resources.server.resources.FileResource;
import com.feature.resources.server.resources.GraphicResource;
import com.google.common.collect.Sets;

import javax.ws.rs.core.Application;
import java.util.Set;

public class MainJerseyApplciation extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classesSet = Sets.newHashSet();
        classesSet.add(GraphicResource.class);
        classesSet.add(FileResource.class);
        return classesSet;
    }
}
