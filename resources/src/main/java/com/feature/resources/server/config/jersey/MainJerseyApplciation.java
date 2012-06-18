package com.feature.resources.server.config.jersey;

import com.feature.resources.server.resources.FileResource;
import com.feature.resources.server.resources.GraphicResource;
import com.feature.resources.server.resources.TagResource;
import com.feature.resources.server.resources.WorkspaceResource;
import com.google.common.collect.Sets;

import javax.ws.rs.core.Application;
import java.util.Set;

public class MainJerseyApplciation extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classesSet = Sets.newHashSet();
        classesSet.add(GraphicResource.class);
        classesSet.add(FileResource.class);
        classesSet.add(WorkspaceResource.class);
        classesSet.add(TagResource.class);
        return classesSet;
    }
}
