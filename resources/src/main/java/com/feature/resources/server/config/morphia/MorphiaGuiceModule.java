package com.feature.resources.server.config.morphia;

import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.validation.MorphiaValidation;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MorphiaGuiceModule extends AbstractModule {

    public static final Logger LOGGER = LoggerFactory.getLogger(MorphiaGuiceModule.class);

    @Override
    protected void configure() {

    }

    @Provides
    public Datastore provideMorphia() {
        try {

            Mongo mongo = new Mongo("127.0.0.1", 27017);
            Morphia morphia = new Morphia();
            morphia.map(Graphic.class);
            Datastore ds = morphia.createDatastore(mongo, "testDb");
            MorphiaValidation morphiaValidation = new MorphiaValidation();
            morphiaValidation.applyTo(morphia);
            ds.ensureIndexes();
            ds.ensureCaps();
            return ds;
        } catch (UnknownHostException e) {
            LOGGER.error("UnKnowHostException:",e);
        } catch (MongoException e) {
            LOGGER.error("MongoException:",e);
        }
        return null;
    }

}
